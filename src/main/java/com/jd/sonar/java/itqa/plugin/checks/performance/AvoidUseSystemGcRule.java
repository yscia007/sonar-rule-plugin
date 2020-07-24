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
import org.sonar.java.checks.helpers.ExpressionsHelper;
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.java.model.ExpressionUtils;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.Collections;
import java.util.List;

/**
 * @author: zhangliwei29
 * @date: 2019/1/21
 */
@Rule(key = "QA104")
public class AvoidUseSystemGcRule extends AbstractMethodDetection {

    private static final String GC = ".gc";
    private static final String RUNTIME_GC = "Runtime.getRuntime.gc";
    private static final String JAVA_LANG_SYSTEM = "java.lang.System";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.METHOD_INVOCATION);
    }

    @Override
    protected List<MethodMatcher> getMethodInvocationMatchers() {
        return Collections.singletonList(MethodMatcher.create().typeDefinition(JAVA_LANG_SYSTEM).name("gc").withoutParameter());
    }

    @Override
    public void visitNode(Tree tree) {
        MethodInvocationTree mit = (MethodInvocationTree) tree;
        if (mit.arguments().isEmpty() && mit.methodSelect().is(Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree mset = (MemberSelectExpressionTree) mit.methodSelect();
            if (isRuntimeGcCall(mset)) {
                reportIssue(mset, "避免手动调用gc方法进行垃圾回收");
            }
        }
        super.visitNode(tree);
    }

    @Override
    protected void onMethodInvocationFound(MethodInvocationTree mit) {
        reportIssue(ExpressionUtils.methodName(mit), "避免手动调用gc方法进行垃圾回收");
    }

    private static Boolean isRuntimeGcCall(MemberSelectExpressionTree mset) {
        String expression = ExpressionsHelper.concatenate(mset);
        if (GC.equals(expression) && mset.expression().is(Kind.METHOD_INVOCATION)) {
            MethodInvocationTree mit = (MethodInvocationTree) mset.expression();
            if (mit.arguments().isEmpty() && mit.methodSelect().is(Kind.MEMBER_SELECT)) {
                expression = ExpressionsHelper.concatenate(mit.methodSelect()) + expression;
                return RUNTIME_GC.equals(expression);
            }
        }
        return false;
    }
}

