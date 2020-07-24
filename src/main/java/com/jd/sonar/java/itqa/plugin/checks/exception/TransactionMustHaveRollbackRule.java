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
package com.jd.sonar.java.itqa.plugin.checks.exception;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.java.model.ExpressionUtils;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: yangshuo8
 * @date: 2019-03-01 09:31
 * @desc: description
 */
@Rule(key = "p3c041")
public class TransactionMustHaveRollbackRule extends IssuableSubscriptionVisitor {

    private static final String TRANSACTIONAL_ANNOTATION = "Transactional";
    private static final String ROLLBACK_METHOD = "rollback";
    private static final String ROLLBACKFOR_ARGUMENT = "rollbackFor";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;
        TransactionalClassVisitor classVisitor = new TransactionalClassVisitor();
        classTree.accept(classVisitor);
        if (classVisitor.isTransactional) {
            if (classVisitor.noRollBackArgument) {
                reportIssue(classVisitor.issueTree, "需要在Transactional注解指定rollbackFor。");
            }
        } else {
            List<MethodTree> methodTrees = classTree.members().stream()
                    .filter(t -> t.is(Kind.METHOD))
                    .map(t -> (MethodTree) t)
                    .collect(Collectors.toList());
            if (! methodTrees.isEmpty()) {
                for (MethodTree methodTree : methodTrees) {
                    TransactionalMethodVisitor methodVisitor = new TransactionalMethodVisitor();
                    methodTree.accept(methodVisitor);
                    if (methodVisitor.shouldCheckMethodBlock && !methodVisitor.hasRollBackMethod) {
                        reportIssue(methodTree.simpleName(), "方法\"" + methodTree.simpleName().name() + "\"需要在Transactional注解指定rollbackFor或者在方法中显式的rollback。");
                    }
                }
            }
        }
    }

    private static Boolean hasTransactionalAnnotation(AnnotationTree tree) {
        if (tree.annotationType().is(Kind.IDENTIFIER)) {
            IdentifierTree idf = (IdentifierTree) tree.annotationType();
            return TRANSACTIONAL_ANNOTATION.equals(idf.name());
        }
        return false;
    }

    private static Boolean hasRollBackForArgument(AnnotationTree tree) {
        if (tree.arguments().isEmpty()) {
            return false;
        }
        return tree.arguments().stream()
                .filter(t -> t.is(Kind.ASSIGNMENT))
                .map(t -> (AssignmentExpressionTree) t)
                .map(t -> (IdentifierTree) t.variable())
                .anyMatch(t -> ROLLBACKFOR_ARGUMENT.equals(t.name()));
    }

    private static class TransactionalClassVisitor extends BaseTreeVisitor {

        private Boolean isTransactional = false;
        private Boolean noRollBackArgument = false;
        private AnnotationTree issueTree;

        @Override
        public void visitClass(ClassTree tree) {
            scan(tree.modifiers());
        }

        @Override
        public void visitModifier(ModifiersTree tree) {
            List<AnnotationTree> annotationTrees = tree.annotations();
            if (! annotationTrees.isEmpty()) {
                for (AnnotationTree annotationTree : annotationTrees) {
                    isTransactional = hasTransactionalAnnotation(annotationTree);
                    noRollBackArgument = !hasRollBackForArgument(annotationTree);
                    if (isTransactional && noRollBackArgument) {
                        issueTree = annotationTree;
                        break;
                    }
                }
            }
        }
    }

    private static class TransactionalMethodVisitor extends BaseTreeVisitor {

        private Boolean hasRollBackMethod = false;
        private Boolean shouldCheckMethodBlock = false;

        @Override
        public void visitMethod(MethodTree tree) {
            scan(tree.modifiers());
            if (shouldCheckMethodBlock) {
                scan(tree.block());
            }
        }

        @Override
        public void visitModifier(ModifiersTree tree) {
            List<AnnotationTree> annotationTrees = tree.annotations();
            if (! annotationTrees.isEmpty()) {
                shouldCheckMethodBlock = annotationTrees.stream()
                        .anyMatch(t -> shouldCheckRollBackMethod(t));
            }
        }

        @Override
        public void visitBlock(BlockTree tree) {
            List<StatementTree> statementTrees = tree.body();
            if (!statementTrees.isEmpty()) {
                hasRollBackMethod = statementTrees.stream()
                        .filter(t -> t.is(Kind.EXPRESSION_STATEMENT))
                        .map(t -> (ExpressionStatementTree) t)
                        .filter(t -> t.expression().is(Kind.METHOD_INVOCATION))
                        .map(t -> (MethodInvocationTree) t.expression())
                        .anyMatch(t -> ROLLBACK_METHOD.equals(ExpressionUtils.methodName(t).name()));
            }
        }

        private static Boolean shouldCheckRollBackMethod(AnnotationTree tree) {
            return hasTransactionalAnnotation(tree) && !hasRollBackForArgument(tree);
        }
    }
}
