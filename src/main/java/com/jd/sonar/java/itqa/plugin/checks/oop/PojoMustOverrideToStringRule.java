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
package com.jd.sonar.java.itqa.plugin.checks.oop;

import com.google.common.collect.ImmutableList;
import com.jd.sonar.java.itqa.plugin.checks.helpers.PojoUtils;
import org.sonar.check.Rule;
import org.sonar.java.checks.helpers.ExpressionsHelper;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: yangshuo8
 * @date: 2019/2/27 18:00
 * @desc: description
 */

@Rule(key = "p3c040")
public class PojoMustOverrideToStringRule extends IssuableSubscriptionVisitor {

    private static final String LOMBOK_REGEX = "(Data|lombok.Data|ToString|lombok.ToString)";
    private static final String OVERRIDE_METHOD = "toString";
    private static final String SUPER_METHOD = "super.toString";
    private Deque<String> lombokStack = new ArrayDeque<>();
    private Deque<String> superStack = new ArrayDeque<>();

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;
        if (PojoUtils.isPojo(classTree)) {
            if (hasSuperPojoClass(classTree)) {
                superStack.push(classTree.simpleName().name());
            }
            if (hasLombokAnnotation(classTree)) {
                lombokStack.push(classTree.simpleName().name());
            } else if (!hasOverrideToString(classTree) && lombokStack.isEmpty()) {
                reportIssue(classTree.simpleName(), "没有重载toString方法");
            }
        }
    }

    @Override
    public void leaveNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;
        if (classTree.simpleName() == null) {
            return;
        }
        String className = classTree.simpleName().name();
        if (!lombokStack.isEmpty() && className.equals(lombokStack.peek())) {
            lombokStack.pop();
        }
        if (!superStack.isEmpty() && className.equals(superStack.peek())) {
            superStack.pop();
        }
    }

    @Override
    public void leaveFile(JavaFileScannerContext context) {
        lombokStack.clear();
        superStack.clear();
    }

    private Boolean hasSuperPojoClass(ClassTree tree) {
        if (tree.superClass() == null) {
            return false;
        }
        if (tree.superClass().is(Kind.IDENTIFIER)) {
            return PojoUtils.isPojo(((IdentifierTree) tree.superClass()).name());
        } else if (tree.superClass().is(Kind.PARAMETERIZED_TYPE)) {
            ParameterizedTypeTree ptt = ((ParameterizedTypeTree) tree.superClass());
            if (ptt.type().is(Kind.IDENTIFIER)) {
                return PojoUtils.isPojo(((IdentifierTree) ptt.type()).name());
            }
        }
        return false;
    }

    private Boolean hasOverrideToString(ClassTree tree) {
        boolean isOverride = tree.members().stream()
                .filter(t -> t.is(Kind.METHOD))
                .map(t -> (MethodTree) t)
                .anyMatch(methodTree -> OVERRIDE_METHOD.equals(methodTree.simpleName().name()));

        if (superStack.isEmpty()) {
            return isOverride;
        }
        return isOverride && hasCallSuperToString(tree);
    }

    private Boolean hasCallSuperToString(ClassTree tree) {
        List<MethodTree> methodTreeList = tree.members().stream()
                .filter(t -> t.is(Kind.METHOD))
                .map(t -> (MethodTree) t)
                .filter(t -> OVERRIDE_METHOD.equals(t.simpleName().name()))
                .collect(Collectors.toList());
        if (methodTreeList.isEmpty()) {
            return false;
        }
        MethodTree methodTree = methodTreeList.get(0);
        int length = methodTree.block().body().size();
        List<StatementTree> statementTreeList = methodTree.block().body();
        if (statementTreeList.get(0).is(Kind.EXPRESSION_STATEMENT)) {
            ExpressionStatementTree est = (ExpressionStatementTree) statementTreeList.get(0);
            if (est.expression().is(Kind.METHOD_INVOCATION)) {
                MethodInvocationTree mit = (MethodInvocationTree) est.expression();
                String expression = ExpressionsHelper.concatenate(mit.methodSelect());
                return SUPER_METHOD.equals(expression);
            }
        } else if (statementTreeList.get(length - 1).is(Kind.RETURN_STATEMENT)) {
            ReturnStatementTree rst = (ReturnStatementTree) statementTreeList.get(length - 1);
            if (rst.expression().is(Kind.METHOD_INVOCATION)) {
                MethodInvocationTree mit = (MethodInvocationTree) rst.expression();
                String expression = ExpressionsHelper.concatenate(mit.methodSelect());
                return SUPER_METHOD.equals(expression);
            }
        }
        return false;
    }

    private Boolean hasLombokAnnotation(ClassTree tree) {
        List<AnnotationTree> annotations = tree.modifiers().annotations();
        if (annotations.isEmpty()) {
            return false;
        }
        for (AnnotationTree annotationTree : annotations) {
            TypeTree typeTree = annotationTree.annotationType();
            boolean lombokDotAnnotation = typeTree.is(Kind.MEMBER_SELECT) &&
                    (ExpressionsHelper.concatenate((ExpressionTree) typeTree)).matches(LOMBOK_REGEX);
            boolean noDotAnnotation = typeTree.is(Kind.IDENTIFIER) &&
                    ((IdentifierTree) typeTree).name().matches(LOMBOK_REGEX);
            if (lombokDotAnnotation || noDotAnnotation) {
                return true;
            }
        }
        return false;
    }
}