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
package com.jd.sonar.java.itqa.plugin.checks.other;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

/**
 * @author: zhangliwei29
 *  @date: 2019/2/18
 * */

@Rule(key = "p3c022")
public class AvoidNewDateGetTimeRule extends AbstractMethodDetection {

    private static final String GET_TIME_METHOD = "getTime";

    @Override
    protected List<MethodMatcher> getMethodInvocationMatchers() {
        return ImmutableList.of(MethodMatcher.create().typeDefinition("java.util.Date").name("<init>").withoutParameter());
    }

    @Override
    protected void onConstructorFound(NewClassTree newClassTree) {
        if (newClassTree.parent().is(Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree mset = (MemberSelectExpressionTree) newClassTree.parent();
            String name = mset.identifier().name();
            if (GET_TIME_METHOD.equals(name)) {
                reportIssue(newClassTree, "获取当前毫秒数用 System.currentTimeMillis(); 而不是 new Date().getTime()。");
            }
        }
    }
}
