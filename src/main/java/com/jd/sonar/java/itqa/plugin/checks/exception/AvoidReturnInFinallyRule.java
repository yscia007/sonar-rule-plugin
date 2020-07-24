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

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author: zhangliwei29
 *  @date: 2019/1/28
 * */

@Rule(key = "p3c012")
public class AvoidReturnInFinallyRule extends BaseTreeVisitor implements JavaFileScanner {
    private final Deque<Tree.Kind> treeKindStack = new LinkedList<>();
    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        treeKindStack.clear();
        scan(context.getTree());
    }

    @Override
    public void visitTryStatement(TryStatementTree tree) {
        scan(tree.resourceList());
        scan(tree.block());
        scan(tree.catches());
        BlockTree finallyBlock = tree.finallyBlock();
        if (finallyBlock != null) {
            treeKindStack.push(finallyBlock.kind());
            scan(finallyBlock);
            treeKindStack.pop();
        }
    }

    @Override
    public void visitMethod(MethodTree tree) {
        treeKindStack.push(tree.kind());
        super.visitMethod(tree);
        treeKindStack.pop();
    }

    @Override
    public void visitForStatement(ForStatementTree tree) {
        treeKindStack.push(tree.kind());
        super.visitForStatement(tree);
        treeKindStack.pop();
    }

    @Override
    public void visitForEachStatement(ForEachStatement tree) {
        treeKindStack.push(tree.kind());
        super.visitForEachStatement(tree);
        treeKindStack.pop();
    }

    @Override
    public void visitWhileStatement(WhileStatementTree tree) {
        treeKindStack.push(tree.kind());
        super.visitWhileStatement(tree);
        treeKindStack.pop();
    }

    @Override
    public void visitDoWhileStatement(DoWhileStatementTree tree) {
        treeKindStack.push(tree.kind());
        super.visitDoWhileStatement(tree);
        treeKindStack.pop();
    }

    @Override
    public void visitSwitchStatement(SwitchStatementTree tree) {
        treeKindStack.push(tree.kind());
        super.visitSwitchStatement(tree);
        treeKindStack.pop();
    }

    @Override
    public void visitReturnStatement(ReturnStatementTree tree) {
        reportIssue(tree.returnKeyword(), tree.kind());
        super.visitReturnStatement(tree);
    }

    @Override
    public void visitThrowStatement(ThrowStatementTree tree) {
        reportIssue(tree.throwKeyword(), tree.kind());
        super.visitThrowStatement(tree);
    }

    @Override
    public void visitContinueStatement(ContinueStatementTree tree) {
        reportIssue(tree.continueKeyword(), tree.kind());
        super.visitContinueStatement(tree);
    }

    @Override
    public void visitBreakStatement(BreakStatementTree tree) {
        reportIssue(tree.breakKeyword(), tree.kind());
        super.visitBreakStatement(tree);
    }

    private void reportIssue(SyntaxToken syntaxToken, Tree.Kind jumpKind) {
        if (isAbruptFinallyBlock(jumpKind)) {
            context.reportIssue(this, syntaxToken, "不要在finally块中执行 " + syntaxToken.text() + " 语句。");
        }
    }

    private boolean isAbruptFinallyBlock(Tree.Kind jumpKind) {
        if (treeKindStack.isEmpty()) {
            return false;
        }
        Tree.Kind blockKind = treeKindStack.peek();
        switch (blockKind) {
            case BLOCK:
                return true;
            case FOR_STATEMENT:
            case FOR_EACH_STATEMENT:
            case WHILE_STATEMENT:
            case DO_STATEMENT:
            case SWITCH_STATEMENT:
                return handleControlFlowInFinally(jumpKind);
            case METHOD:
            default:
                return false;
        }
    }

    private boolean handleControlFlowInFinally(Tree.Kind jumpKind) {
        if (jumpKind == Tree.Kind.BREAK_STATEMENT || jumpKind == Tree.Kind.CONTINUE_STATEMENT) {
            return false;
        }
        Tree.Kind parentOfControlFlowStatement = treeKindStack.stream()
                .filter(t -> t == Tree.Kind.BLOCK || t == Tree.Kind.METHOD)
                .findFirst()
                .orElse(Tree.Kind.METHOD);
        return parentOfControlFlowStatement == Tree.Kind.BLOCK;
    }

}
