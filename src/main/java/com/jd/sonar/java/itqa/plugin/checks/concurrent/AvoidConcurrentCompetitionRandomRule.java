/*
 * Copyright 2018-2020 JD.com, Inc. QA Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jd.sonar.java.itqa.plugin.checks.concurrent;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.java.checks.helpers.ExpressionsHelper;
import org.sonar.java.model.ModifiersUtils;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019/3/19
 * @desc: [Recommended] Avoid using Random instance by multiple threads.
 * Although it is safe to share this instance, competition on the same seed will damage performance.
 * Note: Random instance includes instances of java.util.Random and Math.random().
 */
@Rule(key = "p3c027")
public class AvoidConcurrentCompetitionRandomRule extends IssuableSubscriptionVisitor {

    private static final String THREAD = "Thread";
    private static final String JAVA_UTIL_RANDOM = "java.util.Random";
    private static final String MATH_RANDOM = "Math.random";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;
        if (! hasThreadSuperClass(classTree)) {
            return;
        }
        ThreadClassVisitor classVisitor = new ThreadClassVisitor();
        classTree.accept(classVisitor);
        if (classVisitor.issueTrees.isEmpty()) {
            return;
        }
        for (Tree issueTree : classVisitor.issueTrees) {
            reportIssue(issueTree, "在多线程场景中应避免共享 Random 实例");
        }
    }

    private static Boolean hasThreadSuperClass(ClassTree tree) {
        return tree.superClass() != null && THREAD.equals(tree.superClass().symbolType().name());
    }

    private static class ThreadClassVisitor extends BaseTreeVisitor {

        private List<String> randomVariables = new ArrayList<>();
        private List<Tree> issueTrees = new ArrayList<>();

        @Override
        public void visitClass(ClassTree tree) {
            scan(tree.members());
        }

        @Override
        public void visitVariable(VariableTree tree) {
            if (isStaticRandomVariable(tree)) {
                randomVariables.add(tree.simpleName().name());
            }else if (isLocalMathRandom(tree)) {
                issueTrees.add(tree.initializer());
            }
        }

        @Override
        public void visitMemberSelectExpression(MemberSelectExpressionTree tree) {
            if (randomVariables.isEmpty()) {
                return;
            }
            if (tree.expression().is(Kind.IDENTIFIER)) {
                String name = ((IdentifierTree) tree.expression()).name();
                if (randomVariables.contains(name)) {
                    issueTrees.add(tree);
                }
            }
        }

        private static Boolean isStaticRandomVariable(VariableTree tree) {
            return JAVA_UTIL_RANDOM.equals(tree.type().symbolType().fullyQualifiedName()) &&
                    ModifiersUtils.hasModifier(tree.modifiers(), Modifier.STATIC) &&
                    tree.parent().is(Kind.CLASS);
        }

        private static Boolean isLocalMathRandom(VariableTree tree) {
            if (tree.parent().is(Kind.CLASS) || tree.initializer() == null) {
                return false;
            }
            if (tree.initializer().is(Kind.METHOD_INVOCATION)) {
                MethodInvocationTree mit = (MethodInvocationTree) tree.initializer();
                return mit.methodSelect().is(Kind.MEMBER_SELECT) &&
                        MATH_RANDOM.equals(ExpressionsHelper.concatenate(mit.methodSelect()));
            }
            return false;
        }
    }
}