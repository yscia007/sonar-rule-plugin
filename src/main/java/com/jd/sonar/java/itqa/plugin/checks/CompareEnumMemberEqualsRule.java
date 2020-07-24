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
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Collections;
import java.util.List;

/**
 * @author: zhangliwei29
 * @date: 2019/1/21
 */

@Rule(key = "QA109")
public class CompareEnumMemberEqualsRule extends AbstractMethodDetection {

    private static final String JAVA_LANG_OBJECT = "java.lang.Object";

    @Override
    protected List<MethodMatcher> getMethodInvocationMatchers() {
        return Collections.singletonList(MethodMatcher.create().name("equals").parameters(JAVA_LANG_OBJECT));
    }

    @Override
    protected void onMethodInvocationFound(MethodInvocationTree mit) {
        if (mit.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree mse = (MemberSelectExpressionTree) mit.methodSelect();
            if (isEnum(mse.expression()) || isEnum(mit.arguments().get(0))) {
                reportIssue(mse.identifier(), "枚举类型判断相等时请使用 \"==\" 代替 \"equals\"");
            }
        }
    }

    private static Boolean isEnum(ExpressionTree exp) {
        return exp.symbolType().symbol().isEnum();
    }

}
