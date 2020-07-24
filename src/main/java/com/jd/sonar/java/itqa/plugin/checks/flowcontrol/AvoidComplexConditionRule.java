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
package com.jd.sonar.java.itqa.plugin.checks.flowcontrol;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019/3/17
 * @desc: [Mandatory] Do not use complicated statements in conditional statements (except for frequently used methods
 * like getXxx/isXxx). Use boolean variables to store results of complicated statements temporarily will increase
 * the code's readability.
 */
@Rule(key = "p3c021")
public class AvoidComplexConditionRule extends IssuableSubscriptionVisitor {

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.IF_STATEMENT, Kind.CONDITIONAL_EXPRESSION);
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Kind.IF_STATEMENT)) {
            IfStatementTree ift = (IfStatementTree) tree;
            ConditionVisitor conditionVisitor = new ConditionVisitor();
            ift.condition().accept(conditionVisitor);
            if (conditionVisitor.conditionalOperatorCount > 1) {
                reportIssue(ift.condition(), "不要在条件判断中执行复杂的语句");
            }
        } else if (tree.is(Kind.CONDITIONAL_EXPRESSION)) {
            ConditionalExpressionTree cet = (ConditionalExpressionTree) tree;
            ConditionVisitor conditionVisitor = new ConditionVisitor();
            cet.condition().accept(conditionVisitor);
            if (conditionVisitor.conditionalOperatorCount > 1) {
                reportIssue(cet.condition(), "不要在条件判断中执行复杂的语句");
            }
        }
    }

    private static class ConditionVisitor extends BaseTreeVisitor {

        private Integer conditionalOperatorCount = 0;

        @Override
        public void visitBinaryExpression(BinaryExpressionTree tree) {
            if (hasConditionalOperator(tree)) {
                conditionalOperatorCount++;
            }
            super.visitBinaryExpression(tree);
        }

        private static Boolean hasConditionalOperator(ExpressionTree tree) {
            return tree.is(Kind.CONDITIONAL_AND) || tree.is(Kind.CONDITIONAL_OR);
        }
    }
}
