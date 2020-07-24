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
package com.jd.sonar.java.itqa.plugin.checks.performance;

import com.google.common.collect.ImmutableList;
import com.jd.sonar.java.itqa.plugin.checks.helpers.PojoUtils;
import org.sonar.check.Rule;
import org.sonar.java.model.ExpressionUtils;
import org.sonar.java.model.ModifiersUtils;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author: yangshuo27
 * @date: 2019/3/19 16:12
 * @desc: Avoid use recursive function rule implementation.
 */
@Rule(key = "QA105")
public class AvoidUseRecursiveFunctionRule extends IssuableSubscriptionVisitor {

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;
        boolean shouldSkip = isAbstract(classTree) || PojoUtils.isPojo(classTree);
        if (shouldSkip) {
            return;
        }
        ClassVisitor classVisitor = new ClassVisitor();
        classTree.accept(classVisitor);
        if (classVisitor.issueTrees.isEmpty()) {
            return;
        }
        for (Tree issueTree : classVisitor.issueTrees) {
            reportIssue(issueTree, "尽量避免在代码中使用递归函数");
        }
    }

    private static Boolean isAbstract(ClassTree tree) {
        return ModifiersUtils.hasModifier(tree.modifiers(), Modifier.ABSTRACT);
    }

    private static class ClassVisitor extends BaseTreeVisitor {

        private String enclosingMethod = "";
        private List<Tree> issueTrees = new ArrayList<>();

        @Override
        public void visitClass(ClassTree tree) {
            scan(tree.members());
        }

        @Override
        public void visitMethod(MethodTree tree) {
            if (tree.is(Kind.CONSTRUCTOR) || tree.symbol().isUnknown()) {
                return;
            }
            enclosingMethod = tree.simpleName().name();
            super.visitMethod(tree);
        }

        @Override
        public void visitMethodInvocation(MethodInvocationTree mit) {
            String name = ExpressionUtils.methodName(mit).name();
            boolean shouldCheck = enclosingMethod != null && enclosingMethod.equals(name) && mit.methodSelect().is(Kind.IDENTIFIER);
            if (shouldCheck) {
                if (mit.symbol().isUnknown() || !mit.symbol().isMethodSymbol()) {
                    return;
                }
                MethodTree methodTree = ExpressionUtils.getEnclosingMethod(mit);
                if (hasRecursiveMethodCall(methodTree, mit)) {
                    issueTrees.add(mit);
                }
            }
            super.visitMethodInvocation(mit);
        }

        private static Boolean hasRecursiveMethodCall(MethodTree methodTree, MethodInvocationTree mit) {
            boolean hasSameParametersCount = methodTree.parameters().size() == mit.arguments().size();
            Type methodReturnType = methodTree.returnType().symbolType();
            Symbol.MethodSymbol symbol = (Symbol.MethodSymbol) mit.symbol();
            boolean hasSameReturnType = methodReturnType.name().equals(symbol.returnType().name());
            if (hasSameParametersCount && hasSameReturnType) {
                List<VariableTree> methodParams = methodTree.parameters();
                Arguments mitParams = mit.arguments();
                int length = methodParams.size();
                for (int i = 0; i < length; i++) {
                    VariableTree methodParamTree = methodParams.get(i);
                    ExpressionTree mitParamTree = mitParams.get(i);
                    boolean isSameType = methodParamTree.symbol().type().name().equals(mitParamTree.symbolType().name());
                    if (!isSameType) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    }
}
