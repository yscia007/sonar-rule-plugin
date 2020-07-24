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
import org.sonar.java.checks.helpers.ExpressionsHelper;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019/3/18 18:29
 * @desc: [Mandatory] Do not remove or add elements to a collection in a foreach loop. Please use Iterator to remove an item.
 * Iterator object should be synchronized when executing concurrent operations.
 */
@Rule(key = "p3c016")
public class DontModifyInForeachCircleRule extends IssuableSubscriptionVisitor {

    private static final String ADD = ".add";
    private static final String REMOVE = ".remove";
    private static final String CLEAR = ".clear";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.FOR_EACH_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        ForEachStatement forEachStatement = (ForEachStatement) tree;
        if (forEachStatement.expression().is(Kind.IDENTIFIER)) {
            String name = ((IdentifierTree) forEachStatement.expression()).name();
            ForEachBlockVisitor blockVisitor = new ForEachBlockVisitor(name);
            forEachStatement.statement().accept(blockVisitor);
            if (blockVisitor.issueTrees.isEmpty()) {
                return;
            }
            for (Tree issueTree : blockVisitor.issueTrees) {
                reportIssue(issueTree, "不要在 foreach 循环里进行元素的 remove/add 操作");
            }
        }
    }

    private static class ForEachBlockVisitor extends BaseTreeVisitor {

        private String name;
        private List<Tree> issueTrees = new ArrayList<>();

        public ForEachBlockVisitor(String name) {
            this.name = name;
        }

        @Override
        public void visitMemberSelectExpression(MemberSelectExpressionTree tree) {
            String expression = ExpressionsHelper.concatenate(tree);
            boolean shouldProcess = expression.equals(name + ADD) || expression.equals(name + REMOVE) || expression.equals(name + CLEAR);
            if (shouldProcess) {
                issueTrees.add(tree);
            }
        }
    }
}