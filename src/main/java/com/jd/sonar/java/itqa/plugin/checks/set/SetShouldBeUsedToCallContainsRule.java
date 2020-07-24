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
package com.jd.sonar.java.itqa.plugin.checks.set;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.java.model.ExpressionUtils;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiqiao on 2020/3/25.
 * @desc: When contains method is called frequently, set should be used.
 * 暂只考虑list类型调用
 */
@Rule(key = "QAS006")
public class SetShouldBeUsedToCallContainsRule extends IssuableSubscriptionVisitor {

    private static String MESSAGE = "频繁调用 Collection.contains 方法请使用 Set.";
    private static final String CONTAINS_METHOD = "contains";
    private static final String LIST_COLLECTION = "ArrayList";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.FOR_EACH_STATEMENT,Tree.Kind.DO_STATEMENT,Tree.Kind.WHILE_STATEMENT,Tree.Kind.FOR_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {

        LoopBlockVisitor blockVisitor = new LoopBlockVisitor(CONTAINS_METHOD);

        if (tree.is(Tree.Kind.FOR_STATEMENT)){
            ForStatementTree forStatementTree = (ForStatementTree) tree;
            forStatementTree.statement().accept(blockVisitor);
        }else if(tree.is(Tree.Kind.FOR_EACH_STATEMENT)){
            ForEachStatement ForEachStatement = (ForEachStatement) tree;
            ForEachStatement.statement().accept(blockVisitor);
        }else if(tree.is(Tree.Kind.DO_STATEMENT)){
            DoWhileStatementTree doWhileStatementTree = (DoWhileStatementTree) tree;
            doWhileStatementTree.statement().accept(blockVisitor);
        }else if(tree.is(Tree.Kind.WHILE_STATEMENT)){
            WhileStatementTree whileStatementTree = (WhileStatementTree) tree;
            whileStatementTree.statement().accept(blockVisitor);
        }

        if (blockVisitor.issueTrees.isEmpty()) {
            return;
        }
        for (Tree issueTree : blockVisitor.issueTrees) {
            reportIssue(issueTree, MESSAGE);
        }
    }

    private static class LoopBlockVisitor extends BaseTreeVisitor {

        private String methodName;
        private List<Tree> issueTrees = new ArrayList<>(16);

        public LoopBlockVisitor(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public void visitMethodInvocation(MethodInvocationTree tree) {
            String name  = ExpressionUtils.methodName(tree).name();
            boolean shouldProcess = name.equals(methodName);
            String varType = tree.symbol().owner().name();
            boolean isListCallMethod = varType.equals(LIST_COLLECTION);
            if (shouldProcess && isListCallMethod) {
                issueTrees.add(tree);
            }
        }
    }
}
