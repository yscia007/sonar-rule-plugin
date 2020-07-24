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
 * @author: zhangliwei29
 *  @date: 2019/1/29
 * */

@Rule(key = "p3c017")
public class NeedBraceRule extends IssuableSubscriptionVisitor {

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.IF_STATEMENT, Kind.FOR_EACH_STATEMENT, Kind.FOR_STATEMENT, Kind.WHILE_STATEMENT, Kind.DO_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        switch(tree.kind()) {
            case WHILE_STATEMENT:
                WhileStatementTree whileStatementTree = (WhileStatementTree)tree;
                checkStatement(whileStatementTree.whileKeyword(), whileStatementTree.statement());
                break;
            case DO_STATEMENT:
                DoWhileStatementTree doWhileStatementTree = (DoWhileStatementTree)tree;
                checkStatement(doWhileStatementTree.doKeyword(), doWhileStatementTree.statement());
                break;
            case FOR_STATEMENT:
                ForStatementTree forStatementTree = (ForStatementTree)tree;
                checkStatement(forStatementTree.forKeyword(), forStatementTree.statement());
                break;
            case FOR_EACH_STATEMENT:
                ForEachStatement forEachStatement = (ForEachStatement)tree;
                checkStatement(forEachStatement.forKeyword(), forEachStatement.statement());
                break;
            case IF_STATEMENT:
                checkIfStatement((IfStatementTree)tree);
                break;
            default:
                break;
        }

    }

    private void checkIfStatement(IfStatementTree ifStmt) {
        checkStatement(ifStmt.ifKeyword(), ifStmt.thenStatement());
        StatementTree elseStmt = ifStmt.elseStatement();
        if (elseStmt != null && !elseStmt.is(Kind.IF_STATEMENT)) {
            checkStatement(ifStmt.elseKeyword(), elseStmt);
        }

    }

    private void checkStatement(SyntaxToken reportToken, StatementTree statement) {
        if (statement.is(Kind.BLOCK)) {
            return;
        }
        reportIssue(reportToken, reportToken.text() + "语句缺少大括号。");
    }
}
