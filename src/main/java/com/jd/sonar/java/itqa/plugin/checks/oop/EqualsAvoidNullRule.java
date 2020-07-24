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

import org.sonar.check.Rule;
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.java.matcher.TypeCriteria;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author: zhangliwei29
 *  @date: 2019/2/14
 * */

@Rule(key = "p3c013")
public class EqualsAvoidNullRule extends AbstractMethodDetection {

    private static final String JAVA_LANG_OBJECT = "java.lang.Object";

    @Override
    protected List<MethodMatcher> getMethodInvocationMatchers() {
        return Collections.singletonList(MethodMatcher.create().name("equals").typeDefinition(TypeCriteria.anyType()).parameters(JAVA_LANG_OBJECT));
    }

    @Override
    protected void onMethodInvocationFound(MethodInvocationTree mit) {
        if (needToCheck(mit) && mit.methodSelect().is(Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree mse = (MemberSelectExpressionTree) mit.methodSelect();
            if (hasPotentialException(mit)) {
                reportIssue(mse.identifier(), "Object 的 equals 方法容易抛空指针异常，应使用常量或确定有值的对象来调用 equals");
            }
        }
    }

    private static Boolean isConstant(@Nullable MemberSelectExpressionTree tree) {
        if (tree.expression().is(Kind.IDENTIFIER)) {
            IdentifierTree idf = (IdentifierTree) tree.expression();
            return idf.symbol().isStatic() && idf.symbol().isFinal();
        }
        return tree.expression().is(Kind.STRING_LITERAL, Kind.NULL_LITERAL, Kind.BOOLEAN_LITERAL);
    }

    private static Boolean hasPotentialException(MethodInvocationTree mit) {
        MemberSelectExpressionTree mset = (MemberSelectExpressionTree) mit.methodSelect();
        return !isConstant(mset) && isArgumentHasValue(mit.arguments().get(0));
    }

    private static Boolean isArgumentHasValue(Tree tree) {
        if (tree.is(Kind.IDENTIFIER)) {
            IdentifierTree idf = (IdentifierTree) tree;
            return idf.symbol().isStatic() && idf.symbol().isFinal();
        }
        return tree.is(Kind.STRING_LITERAL, Kind.NULL_LITERAL, Kind.BOOLEAN_LITERAL);
    }

    private static Boolean needToCheck(MethodInvocationTree mit) {
        return mit.parent().is(Kind.IF_STATEMENT) ||
                (mit.parent().is(Kind.LOGICAL_COMPLEMENT) && mit.parent().parent().is(Kind.IF_STATEMENT)) ||
                mit.parent().is(Kind.RETURN_STATEMENT) ||
                mit.parent().parent().is(Kind.RETURN_STATEMENT);
    }
}
