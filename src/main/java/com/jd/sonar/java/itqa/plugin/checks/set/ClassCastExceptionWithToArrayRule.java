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
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.TypeCastTree;

import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019-02-19 18:08
 * @desc: description
 */
@Rule(key = "p3c029")
public class ClassCastExceptionWithToArrayRule extends IssuableSubscriptionVisitor {

    private static final String METHOD_NAME = "toArray";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.TYPE_CAST);
    }

    @Override
    public void visitNode(Tree tree) {
        boolean isArrayType = false;
        TypeCastTree typeCastTree = (TypeCastTree) tree;
        if (typeCastTree.type().is(Kind.ARRAY_TYPE)) {
            isArrayType = true;
        }
        if (isArrayType && typeCastTree.expression().is(Kind.METHOD_INVOCATION)) {
            MethodInvocationTree mit = (MethodInvocationTree) typeCastTree.expression();
            if (mit.methodSelect().is(Kind.MEMBER_SELECT)) {
                String methodName = ExpressionUtils.methodName(mit).name();
                int argumentCount = mit.arguments().size();
                if (METHOD_NAME.equals(methodName) && (argumentCount == 0)) {
                    reportIssue(ExpressionUtils.methodName(mit), "集合转数组时必须使用集合的toArray(T[] array)方法");
                }
            }
        }
    }
}
