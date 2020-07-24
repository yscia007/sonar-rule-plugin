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

import org.sonar.check.Rule;
import org.sonar.java.model.ModifiersUtils;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.*;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created with IntelliJ IDEA.
 * @author: yangshuo27
 * @date: 2019/3/20 17:00
 * @desc: Avoid Use Synchronized In While Loop Rule Implementation.
 */

@Rule(key = "QA108")
public class AvoidUseSynchronizedInWhileLoopRule extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;
    private Deque<Boolean> loopStack;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        this.loopStack = new ArrayDeque<>();
        scan(context.getTree());
    }

    @Override
    public void visitForStatement(ForStatementTree tree) {
        loopStack.push(true);
        scan(tree.statement());
        loopStack.pop();
    }

    @Override
    public void visitForEachStatement(ForEachStatement tree) {
        loopStack.push(true);
        scan(tree.statement());
        loopStack.pop();
    }

    @Override
    public void visitWhileStatement(WhileStatementTree tree) {
        loopStack.push(true);
        scan(tree.statement());
        loopStack.pop();
    }

    @Override
    public void visitDoWhileStatement(DoWhileStatementTree tree) {
        loopStack.push(true);
        scan(tree.statement());
        loopStack.pop();
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        boolean shouldSkip = loopStack.isEmpty() || tree.symbol().isUnknown() || !tree.symbol().isMethodSymbol();
        if (shouldSkip) {
            return;
        }
        if (isSynchronized(tree)) {
            context.reportIssue(this, tree, "避免在循环内部调用 synchronized 方法");
        }
    }

    private static Boolean isSynchronized(MethodInvocationTree tree) {
        Symbol.MethodSymbol symbol = (Symbol.MethodSymbol) tree.symbol();
        if (symbol.declaration() == null || symbol.declaration().modifiers() == null) {
            return false;
        }
        return ModifiersUtils.hasModifier(symbol.declaration().modifiers(), Modifier.SYNCHRONIZED);
    }
}
