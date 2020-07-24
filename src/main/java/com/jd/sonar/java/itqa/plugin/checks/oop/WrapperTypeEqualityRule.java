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
package com.jd.sonar.java.itqa.plugin.checks.oop;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019-02-27 14:06
 * @desc: The wrapper classes should be compared by equals method rather than by symbol of '==' directly.
 */
@Rule(key = "p3c037")
public class WrapperTypeEqualityRule extends IssuableSubscriptionVisitor {

    private static final String JAVA_LANG_INTEGER = "java.lang.Integer";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.EQUAL_TO);
    }

    @Override
    public void visitNode(Tree tree) {
        BinaryExpressionTree bet = (BinaryExpressionTree) tree;
        if (bet != null && isWrapperType(bet.leftOperand()) && isWrapperType(bet.rightOperand())) {
            reportIssue(bet.operatorToken(), "应使用'equals'方法代替'=='");
        }
    }

    private Boolean isWrapperType(Tree tree) {
        if (tree.is(Kind.IDENTIFIER)) {
            IdentifierTree idf = (IdentifierTree) tree;
            return JAVA_LANG_INTEGER.equals(idf.symbolType().fullyQualifiedName());
        } else if (tree.is(Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree mset = (MemberSelectExpressionTree) tree;
            return JAVA_LANG_INTEGER.equals(mset.symbolType().fullyQualifiedName());
        } else if (tree.is(Kind.METHOD_INVOCATION)) {
            MethodInvocationTree mit = (MethodInvocationTree) tree;
            return JAVA_LANG_INTEGER.equals(mit.symbolType().fullyQualifiedName());
        } else if (tree.is(Kind.NEW_CLASS)) {
            NewClassTree nct = (NewClassTree) tree;
            return JAVA_LANG_INTEGER.equals(nct.symbolType().fullyQualifiedName());
        }
        return false;
    }
}
