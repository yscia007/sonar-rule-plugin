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


import org.sonar.check.Rule;
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.Collections;
import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019/3/18
 * @desc: When using regex, precompile needs to be done in order to increase the matching performance.
 * Note: Do not define Pattern pattern = Pattern.compile(.); within method body.
 */
@Rule(key = "p3c048")
public class AvoidPatternCompileInMethodRule extends AbstractMethodDetection {

    private static final String JAVA_UTIL_REGEX_PATTERN = "java.util.regex.Pattern";
    private static final String JAVA_LANG_STRING = "java.lang.String";

    @Override
    protected List<MethodMatcher> getMethodInvocationMatchers() {
        return Collections.singletonList(
                MethodMatcher.create().typeDefinition(JAVA_UTIL_REGEX_PATTERN).name("compile").addParameter(JAVA_LANG_STRING));
    }

    @Override
    protected void onMethodInvocationFound(MethodInvocationTree mit) {
        boolean isLocalVariable = !mit.parent().parent().is(Kind.CLASS);
        boolean hasStringLiteralArgument = mit.arguments().get(0).is(Kind.STRING_LITERAL);
        boolean shouldProcess = isLocalVariable && hasStringLiteralArgument;
        if (shouldProcess) {
            if (mit.parent().is(Kind.VARIABLE)) {
                VariableTree vt = (VariableTree) mit.parent();
                reportIssue(vt.simpleName(), "变量\"" + vt.simpleName().name() + "\"应定义为常量或者字段");
            } else {
                reportIssue(mit.parent(), "在使用正则表达式时，利用好其预编译功能，可以有效加快正则匹配速度。");
            }
        }
    }
}