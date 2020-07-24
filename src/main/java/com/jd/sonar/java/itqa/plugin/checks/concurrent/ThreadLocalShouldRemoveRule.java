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
import org.sonar.java.model.ExpressionUtils;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * @author: yangshuo27
 * @date: 2019/02/21
 * @time: 下午23:25
 * @desc: ThreadLocal Should Remove Rule Implementation.
 */
@Rule(key = "p3c023")
public class ThreadLocalShouldRemoveRule extends IssuableSubscriptionVisitor {

    private static final String VARIABLE_TYPE = "ThreadLocal";
    private static final String METHOD_INITIALIZER = "withInitial";
    private static final String METHOD_INITIALVALUE = "initialValue";
    private static final String METHOD_REMOVE = "remove";
    private List<IdentifierTree> identifierTreeList = new ArrayList<>();

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.VARIABLE, Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Tree.Kind.VARIABLE)) {
            VariableTree vt = (VariableTree) tree;
            Type type = vt.type().symbolType();
            if (VARIABLE_TYPE.equals(type.name()) && (! hasThreadLocalInitializer(vt))) {
                identifierTreeList.add(vt.simpleName());
            }
        }
        if (!identifierTreeList.isEmpty() && tree.is(Tree.Kind.METHOD_INVOCATION)) {
            MethodInvocationTree mit = (MethodInvocationTree) tree;
            String methodName = ExpressionUtils.methodName(mit).name();
            if (METHOD_REMOVE.equals(methodName) && mit.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
                MemberSelectExpressionTree mset = (MemberSelectExpressionTree) mit.methodSelect();
                if (mset.expression().is(Tree.Kind.IDENTIFIER)) {
                    IdentifierTree idf = (IdentifierTree) mset.expression();
                    identifierTreeList = identifierTreeList.stream()
                            .filter(varTree -> varTree.is(Tree.Kind.IDENTIFIER))
                            .filter(varTree -> !varTree.name().equals(idf.name()))
                            .collect(Collectors.toList());
                }
            }
        }
    }
    @Override
    public void leaveFile(JavaFileScannerContext context) {
        if (! identifierTreeList.isEmpty()) {
            for (IdentifierTree tree : identifierTreeList) {
                context.reportIssue(this, tree, "ThreadLocal字段\"" + tree.name() + "\"应该至少调用一次remove()方法");
            }
            identifierTreeList.clear();
        }
    }

    private static Boolean hasThreadLocalInitializer(VariableTree tree) {
        if (tree.initializer() != null) {
            if (tree.initializer().is(Tree.Kind.METHOD_INVOCATION)) {
                MethodInvocationTree mit = (MethodInvocationTree) tree.initializer();
                String methodName = ExpressionUtils.methodName(mit).name();
                return METHOD_INITIALIZER.equals(methodName);
            }
            if (tree.initializer().is(Tree.Kind.NEW_CLASS)) {
                NewClassTree newClassTree = (NewClassTree) tree.initializer();
                if (newClassTree.classBody() != null) {
                    return newClassTree.classBody().members().stream()
                            .filter(t -> t.is(Tree.Kind.METHOD))
                            .map(t -> (MethodTree) t)
                            .filter(t -> METHOD_INITIALVALUE.equals(t.simpleName().name())).findFirst().isPresent();
                }
            }
        }
        return false;
    }
}
