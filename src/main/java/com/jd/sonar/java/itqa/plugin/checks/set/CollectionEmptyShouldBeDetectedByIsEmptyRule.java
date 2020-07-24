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
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import java.util.List;

/**
 * Created by weiqiao on 2020/3/6.
 * @date: 2020-03-06 10:02
 * @desc: collection.empty() is rather than collection.size()==0
 */
@Rule(key = "QAS003")
public class CollectionEmptyShouldBeDetectedByIsEmptyRule extends IssuableSubscriptionVisitor {

    private static final String SIZE_METHOD = "size";
    private static final String ZERO_STR = "0";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.EQUAL_TO,Kind.LESS_THAN_OR_EQUAL_TO,Kind.GREATER_THAN);
    }

    @Override
    public void visitNode(Tree tree) {
        BinaryExpressionTree binaryExpressionTree = (BinaryExpressionTree) tree;
        if (binaryExpressionTree != null && isSizeMethod(binaryExpressionTree.leftOperand()) && isZero(binaryExpressionTree.rightOperand())) {
            reportIssue(tree, "集合判空时为提高效率,请使用isEmpty()方法,避免使用.size()==0 || .size()<=0 || .size()>0进行判断.");
        }
    }

    private Boolean isSizeMethod(Tree tree){
        if (tree.is(Kind.METHOD_INVOCATION)) {
            MethodInvocationTree methodInvocationTree = (MethodInvocationTree) tree;
            if(methodInvocationTree.methodSelect().is(Kind.MEMBER_SELECT)){
                MemberSelectExpressionTree MemberSelectExpressionTree = (MemberSelectExpressionTree) methodInvocationTree.methodSelect();
                String methodName = MemberSelectExpressionTree.identifier().toString();
                if(SIZE_METHOD.equals(methodName)){
                    return true;
                }
            }
        }
        return false;
    }

    private Boolean isZero(Tree tree){
        if (tree.is(Kind.INT_LITERAL)){
            LiteralTree literalTree = (LiteralTree) tree;
            if(ZERO_STR.equals(literalTree.value())){
                return true;
            }
        }
        return false;

    }



}


