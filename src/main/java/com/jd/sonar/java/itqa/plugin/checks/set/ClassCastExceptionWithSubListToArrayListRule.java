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
package com.jd.sonar.java.itqa.plugin.checks.set;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.java.model.ExpressionUtils;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019-02-19 15:38
 * @desc: description
 */
@Rule(key = "p3c028")
public class ClassCastExceptionWithSubListToArrayListRule extends IssuableSubscriptionVisitor {

    private static final String RETURN_TYPE = "java.util.List";
    private static final String METHOD_NAME = "subList";
    private static final String CAST_TYPE = "ArrayList";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.TYPE_CAST);
    }

    @Override
    public void visitNode(Tree tree) {
        boolean isArrayListType = false;
        TypeCastTree typeCastTree = (TypeCastTree) tree;
        if (typeCastTree.type().is(Kind.PARAMETERIZED_TYPE)) {
            ParameterizedTypeTree ptt = (ParameterizedTypeTree) typeCastTree.type();
            if (ptt.type().is(Kind.IDENTIFIER)) {
                IdentifierTree ift = (IdentifierTree) ptt.type();
                if (CAST_TYPE.equals(ift.name())) {
                    isArrayListType = true;
                }
            }
        }
        if (isArrayListType && typeCastTree.expression().is(Kind.METHOD_INVOCATION)) {
            MethodInvocationTree mit = (MethodInvocationTree) typeCastTree.expression();
            if (mit.symbol().isUnknown() || !mit.symbol().isMethodSymbol()) {
                return;
            }
            String type = mit.symbolType().fullyQualifiedName();
            if (RETURN_TYPE.equals(type) && mit.methodSelect().is(Kind.MEMBER_SELECT)) {
                String methodName = ExpressionUtils.methodName(mit).name();
                if (METHOD_NAME.equals(methodName)) {
                    reportIssue(ExpressionUtils.methodName(mit), "ArrayList的subList结果不可强制转换为ArrayList");
                }
            }
        }
    }
}
