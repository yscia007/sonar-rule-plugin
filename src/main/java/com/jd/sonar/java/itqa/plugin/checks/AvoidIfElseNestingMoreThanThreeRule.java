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
package com.jd.sonar.java.itqa.plugin.checks;

import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author: lina51
 * @date: 2019/1/29
 */
@Rule(key = "QA111")
public class AvoidIfElseNestingMoreThanThreeRule extends BaseTreeVisitor implements JavaFileScanner {

    private static final int DEFAULT_MAX = 3;

    @RuleProperty(
            description = "Maximum allowed control flow statement nesting depth.",
            defaultValue = "" + DEFAULT_MAX)
    public int max = DEFAULT_MAX;

    private JavaFileScannerContext context;
    private Deque<Tree> nestingLevel;

    @Override
    public void scanFile(final JavaFileScannerContext context) {
        this.context = context;
        this.nestingLevel = new ArrayDeque<>();
        scan(context.getTree());
    }

    @Override
    public void visitIfStatement(IfStatementTree tree) {
        SyntaxToken ifKeyword = tree.ifKeyword();
        checkNesting(ifKeyword);
        nestingLevel.push(ifKeyword);
        visit(tree);
        nestingLevel.pop();
    }

    @Override
    public void visitForStatement(ForStatementTree tree) {
        SyntaxToken forKeyword = tree.forKeyword();
        checkNesting(forKeyword);
        nestingLevel.push(forKeyword);
        super.visitForStatement(tree);
        nestingLevel.pop();
    }

    @Override
    public void visitForEachStatement(ForEachStatement tree) {
        SyntaxToken forKeyword = tree.forKeyword();
        checkNesting(forKeyword);
        nestingLevel.push(forKeyword);
        super.visitForEachStatement(tree);
        nestingLevel.pop();
    }

    @Override
    public void visitWhileStatement(WhileStatementTree tree) {
        SyntaxToken whileKeyword = tree.whileKeyword();
        checkNesting(whileKeyword);
        nestingLevel.push(whileKeyword);
        super.visitWhileStatement(tree);
        nestingLevel.pop();
    }

    @Override
    public void visitDoWhileStatement(DoWhileStatementTree tree) {
        SyntaxToken doKeyword = tree.doKeyword();
        checkNesting(doKeyword);
        nestingLevel.push(doKeyword);
        super.visitDoWhileStatement(tree);
        nestingLevel.pop();
    }

    @Override
    public void visitSwitchStatement(SwitchStatementTree tree) {
        SyntaxToken switchKeyword = tree.switchKeyword();
        checkNesting(switchKeyword);
        nestingLevel.push(switchKeyword);
        super.visitSwitchStatement(tree);
        nestingLevel.pop();
    }

    @Override
    public void visitTryStatement(TryStatementTree tree) {
        SyntaxToken tryKeyword = tree.tryKeyword();
        checkNesting(tryKeyword);
        nestingLevel.push(tryKeyword);
        scan(tree.block());
        nestingLevel.pop();
        scan(tree.resourceList());
        scan(tree.catches());
        scan(tree.finallyBlock());
    }

    private void checkNesting(Tree tree) {
        int size = nestingLevel.size();
        if (size == max) {
            List<JavaFileScannerContext.Location> secondary = new ArrayList<>(size);
            for (Tree element : nestingLevel) {
                secondary.add(new JavaFileScannerContext.Location("Nesting + 1", element));
            }
            context.reportIssue(this, tree, "if/for/while/switch/try 语句嵌套超过" + max + "层啦", secondary, null);
        }
    }

    private void visit(IfStatementTree tree) {
        scan(tree.condition());
        scan(tree.thenStatement());

        StatementTree elseStatementTree = tree.elseStatement();
        if (elseStatementTree != null && elseStatementTree.is(Tree.Kind.IF_STATEMENT)) {
            visit((IfStatementTree) elseStatementTree);
        } else {
            scan(elseStatementTree);
        }
    }
}
