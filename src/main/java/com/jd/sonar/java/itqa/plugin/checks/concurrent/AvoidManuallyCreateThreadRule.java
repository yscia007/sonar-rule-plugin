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
import org.sonar.java.model.ExpressionUtils;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangshuo8
 * @date: 2019-03-20 20:42
 * @desc: New Thread can only be created in ThreadFactory.newThread method,as Runtime.getRuntime().addShutdownHook() parameter,
 * or in static block
 */

@Rule(key = "p3c001")
public class AvoidManuallyCreateThreadRule extends BaseTreeVisitor implements JavaFileScanner {

    private static final String THREAD = "Thread";
    private static final String THREAD_FACTORY = "ThreadFactory";
    private static final String THREAD_POOL_EXECUTOR = "ThreadPoolExecutor";
    private static final String ADD_SHUTDOWN_HOOK = "addShutdownHook";
    private Deque<Boolean> recordStack;
    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        this.recordStack = new ArrayDeque<>();
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        boolean shouldSkip = tree.symbol().isInterface() ||
                tree.symbol().isAbstract() ||
                tree.symbol().isEnum() ||
                isThreadFactoryAsSuperInterface(tree);
        if (shouldSkip) {
            return;
        }
        scan(tree.members());
    }

    @Override
    public void visitBlock(BlockTree tree) {
        if (tree.is(Kind.STATIC_INITIALIZER)) {
            recordStack.push(true);
            scan(tree.body());
            recordStack.pop();
        }
        super.visitBlock(tree);
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        boolean shouldRecord = ADD_SHUTDOWN_HOOK.equals(ExpressionUtils.methodName(tree).name()) && tree.symbol().isMethodSymbol();
        if (shouldRecord) {
            recordStack.push(true);
            scan(tree.arguments());
            recordStack.pop();
        }
    }

    @Override
    public void visitLambdaExpression(LambdaExpressionTree lambdaExpressionTree) {
        boolean shouldRecord = THREAD_FACTORY.equals(lambdaExpressionTree.symbolType().name());
        if (shouldRecord) {
            recordStack.push(true);
            scan(lambdaExpressionTree.body());
            recordStack.pop();
        }
    }

    @Override
    public void visitNewClass(NewClassTree tree) {
        if (tree.identifier().is(Kind.IDENTIFIER)) {
            IdentifierTree idf = (IdentifierTree) tree.identifier();
            boolean shouldSkip = THREAD_FACTORY.equals(idf.name()) || THREAD_POOL_EXECUTOR.equals(idf.name());
            if (!recordStack.isEmpty() || shouldSkip) {
                return;
            }
            if (THREAD.equals(idf.name())) {
                context.reportIssue(this, tree.identifier(), "避免手动创建线程");
            }
        }
    }

    private static Boolean isThreadFactoryAsSuperInterface(ClassTree tree) {
        if (tree.superInterfaces().isEmpty()) {
            return false;
        }
        for (Tree superInterface : tree.superInterfaces()) {
            boolean isThreadFactory = superInterface.is(Kind.IDENTIFIER) && THREAD_FACTORY.equals(((IdentifierTree) superInterface).name());
            if (isThreadFactory) {
                return true;
            }
        }
        return false;
    }
}