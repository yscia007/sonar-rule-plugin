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

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.java.model.ExpressionUtils;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;

/**
 *  [Mandatory] A meaningful thread name is helpful to trace the error information,
 *  so assign a name when creating threads or thread pools.
 *
 *  Detection rule  //TODO should review
 *  1. Use specific constructor while create thread pool
 *  2. Use Executors.defaultThreadFactory() is not allowed
 *
 * @author: yangshuo8
 * @date: 2019-02-22 09:48
 * @desc: description
 */
@Rule(key = "p3c024")
public class ThreadShouldSetNameRule extends AbstractMethodDetection {

    private static final String THREAD_POOL_EXECUTOR = "ThreadPoolExecutor";
    private static final String SCHEDULED_THREAD_POOL_EXECUTOR = "ScheduledThreadPoolExecutor";
    private static final String DEFAULT_METHOD = "defaultThreadFactory";
    private static final String REJECTED_EXECUTION_HANDLER = "RejectedExecutionHandler";
    private static final int ARGUMENT_LENGTH_2 = 2;
    private static final int ARGUMENT_LENGTH_6 = 6;
    private static final int INDEX_1 = 1;
    private static final int SINGLE_LENGTH = 1;

    @Override
    protected List<MethodMatcher> getMethodInvocationMatchers() {
        return ImmutableList.of(
                MethodMatcher.create().typeDefinition("java.util.concurrent.ThreadPoolExecutor").name("<init>").withAnyParameters(),
                MethodMatcher.create().typeDefinition("java.util.concurrent.ScheduledThreadPoolExecutor").name("<init>").withAnyParameters(),
                MethodMatcher.create().typeDefinition("java.lang.Thread").name("<init>").withAnyParameters()
        );
    }

    @Override
    protected void onConstructorFound(NewClassTree newClassTree) {
        IdentifierTree idf = (IdentifierTree) newClassTree.identifier();
        if (THREAD_POOL_EXECUTOR.equals(idf.name())) {
            if ((newClassTree.arguments().size() < ARGUMENT_LENGTH_6 || !checkThreadFactoryArgument(newClassTree.arguments().get(ARGUMENT_LENGTH_6 - INDEX_1)))) {
                reportIssue(idf, "要使用带有ThreadFactory参数的ThreadPoolExecutor构造方法哦，这样你就可以方便的设置线程名字啦。");
            }
        } else if (SCHEDULED_THREAD_POOL_EXECUTOR.equals(idf.name())) {
            if ((newClassTree.arguments().size() < ARGUMENT_LENGTH_2 || !checkThreadFactoryArgument(newClassTree.arguments().get(ARGUMENT_LENGTH_2 - INDEX_1)))) {
                reportIssue(idf, "要使用带有ThreadFactory参数的ScheduledThreadPoolExecutor构造方法哦，这样你就可以方便的设置线程名字啦。");
            }
        }
    }

    private Boolean checkThreadFactoryArgument(Tree tree) {
        if (tree.is(Tree.Kind.LAMBDA_EXPRESSION)) {
            LambdaExpressionTree let = (LambdaExpressionTree) tree;
            if (let.parameters().size() != SINGLE_LENGTH) {
                return false;
            }
        } else if (tree.is(Tree.Kind.NEW_CLASS)) {
            NewClassTree nct = (NewClassTree) tree;
            if (nct.identifier().is(Tree.Kind.IDENTIFIER)) {
                IdentifierTree idf = (IdentifierTree) nct.identifier();
                if (REJECTED_EXECUTION_HANDLER.equals(idf.name())) {
                    return false;
                }
            }
        } else if (tree.is(Tree.Kind.METHOD_INVOCATION)) {
            MethodInvocationTree mit = (MethodInvocationTree) tree;
            String methodName = ExpressionUtils.methodName(mit).name();
            if (DEFAULT_METHOD.equals(methodName)) {
                return false;
            }
        }
        return true;
    }
}
