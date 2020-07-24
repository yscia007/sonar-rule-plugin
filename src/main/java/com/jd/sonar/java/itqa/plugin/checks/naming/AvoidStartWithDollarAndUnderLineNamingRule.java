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
package com.jd.sonar.java.itqa.plugin.checks.naming;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

/**
 * @author: zhangliwei29
 * @date: 2019/2/13
 * @description: All names should not start or end with an underline or a dollar sign.
 * */

@Rule(key = "p3c004")
public class AvoidStartWithDollarAndUnderLineNamingRule extends IssuableSubscriptionVisitor {

    private static final String DOLLAR = "$";
    private static final String UNDERSCORE = "_";
    private static final String MESSAGE = "代码中的命名均不能以'_'或'$'开始，也不能以'_'或'$'结束。";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.CLASS, Kind.INTERFACE, Kind.ENUM, Kind.METHOD, Kind.VARIABLE);
    }

    @Override
    public void visitNode(Tree tree) {
        if (!hasSemantic()) {
            return;
        }
        if (tree.is(Kind.VARIABLE)) {
            IdentifierTree idf = ((VariableTree) tree).simpleName();
            if (idf != null && hasIncorrectNaming(idf)) {
                reportIssue(idf, MESSAGE);
            }
        }else if (tree.is(Kind.METHOD)) {
            IdentifierTree idf = ((MethodTree) tree).simpleName();
            if (idf != null && hasIncorrectNaming(idf)) {
                reportIssue(idf, MESSAGE);
            }
        } else if (tree.is(Kind.CLASS) || tree.is(Kind.INTERFACE) || tree.is(Kind.ENUM)) {
            IdentifierTree idf = ((ClassTree) tree).simpleName();
            if (idf != null && hasIncorrectNaming(idf)) {
                reportIssue(idf, MESSAGE);
            }
        }
    }

    private static Boolean hasIncorrectNaming(IdentifierTree tree) {
        String name = tree.name();
        return name.startsWith(DOLLAR) || name.startsWith(UNDERSCORE) ||
                name.endsWith(DOLLAR) || name.endsWith(UNDERSCORE);
    }
}
