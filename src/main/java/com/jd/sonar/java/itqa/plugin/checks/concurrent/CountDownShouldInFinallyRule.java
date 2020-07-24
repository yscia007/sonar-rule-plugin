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
package com.jd.sonar.java.itqa.plugin.checks.concurrent;


import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.TryStatementTree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: zhangliwei29
 *  @date: 2019/2/12
 * */

@Rule(key = "p3c015")
public class CountDownShouldInFinallyRule extends BaseTreeVisitor implements JavaFileScanner {

    private Deque<Boolean> TryBlockStack = new ArrayDeque<>();
    private JavaFileScannerContext context;
    private static final String COUNT_DOWN = "countDown";

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        TryBlockStack.clear();
        scan(context.getTree());
    }

    @Override
    public void visitTryStatement(TryStatementTree tree) {
        scan(tree.resourceList());
        TryBlockStack.push(true);
        scan(tree.block());
        TryBlockStack.pop();
        scan(tree.catches());
        BlockTree finallyBlock = tree.finallyBlock();
        if (finallyBlock != null) {
            scan(finallyBlock);
        }
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        String methodInvocationName = tree.symbol().name();
        if (COUNT_DOWN.equals(methodInvocationName) && (! TryBlockStack.isEmpty())) {
            context.reportIssue(this, tree, "应在finally块中使用 countDown 方法");
        }
    }
}
