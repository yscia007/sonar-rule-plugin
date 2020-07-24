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
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: xuxiao200
 * @date: 2019/1/21
 * @time: 下午17:02
 * @desc: description
 */
@Rule(key = "QA106")
public class AvoidComputeSizeRepeatedlyInLoopRule extends IssuableSubscriptionVisitor {

    private static final String SIZE = "size";
    private static final String MESSAGE = "循环条件语句中避免变量的重复计算";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.FOR_STATEMENT, Kind.DO_STATEMENT, Kind.WHILE_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Kind.FOR_STATEMENT)) {
            ForStatementTree forStatement = (ForStatementTree) tree;
            if (hasSizeMethodCallInConditon(forStatement.condition())) {
                reportIssue(forStatement.condition(), MESSAGE);
            }
        } else if (tree.is(Kind.WHILE_STATEMENT)){
            WhileStatementTree whileStatement = (WhileStatementTree) tree;
            if (hasSizeMethodCallInConditon(whileStatement.condition())) {
                reportIssue(whileStatement.condition(), MESSAGE);
            }
        } else if (tree.is(Kind.DO_STATEMENT)){
            DoWhileStatementTree doWhileStatement = (DoWhileStatementTree) tree;
            if (hasSizeMethodCallInConditon(doWhileStatement.condition())) {
                reportIssue(doWhileStatement.condition(), MESSAGE);
            }
        }
    }

    private static Boolean hasSizeMethodCallInConditon(ExpressionTree tree) {
        if (tree == null) {
            return false;
        }
        boolean shouldProcess = tree.is(Kind.LESS_THAN, Kind.GREATER_THAN, Kind.LESS_THAN_OR_EQUAL_TO, Kind.GREATER_THAN_OR_EQUAL_TO);
        if (shouldProcess) {
            BinaryExpressionTree bet = (BinaryExpressionTree) tree;
            if (bet.rightOperand().is(Kind.METHOD_INVOCATION)) {
                MethodInvocationTree mit = (MethodInvocationTree) bet.rightOperand();
                if (mit.methodSelect().is(Kind.MEMBER_SELECT)) {
                    MemberSelectExpressionTree mset = (MemberSelectExpressionTree) mit.methodSelect();
                    return SIZE.equals(mset.identifier().name());
                }
            }
        }
        return false;
    }
}
