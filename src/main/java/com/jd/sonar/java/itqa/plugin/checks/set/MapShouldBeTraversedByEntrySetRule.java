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
import org.sonar.java.checks.helpers.ExpressionsHelper;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weiqiao on 2020/3/19.
 * @desc: When both the key and value of Map are in demand, entrySet should be iterated.
 */
@Rule(key = "QAS004")
public class MapShouldBeTraversedByEntrySetRule extends IssuableSubscriptionVisitor {

    private static String MESSAGE = "当需要循环遍历Map同时取得key和value值时,应该迭代entrySet.";
    private static final String KEY_SET_METHOD = "keySet";
    private static final String GET_KEY_VALUE = ".get";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.FOR_EACH_STATEMENT);   //暂不考虑使用while(iter.hasNext())遍历key
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Tree.Kind.FOR_EACH_STATEMENT)) {
            Map<String,Object> resultMap = new HashMap<>(16);
            Boolean isKeySetIterated = false;
            String varCallMethod = "";
            ForEachStatement forEachStatement = (ForEachStatement) tree;
            resultMap = getKeySetContentInExpression(forEachStatement.expression());
            if(!resultMap.isEmpty()){
                isKeySetIterated = (Boolean)resultMap.get("isKeySetIterated");
                varCallMethod = (String)resultMap.get("varCallMethod");
            }
            if (isKeySetIterated && !"".equals(varCallMethod)) {
                ForEachBlockVisitor blockVisitor = new ForEachBlockVisitor(varCallMethod,GET_KEY_VALUE);
                forEachStatement.statement().accept(blockVisitor);
                if (blockVisitor.issueTrees.isEmpty()) {
                    return;
                }
                for (Tree issueTree : blockVisitor.issueTrees) {
                    reportIssue(issueTree, MESSAGE);
                }
            }
        }
    }

    private Map<String,Object> getKeySetContentInExpression(Tree tree) {
        Map<String,Object> returnMap = new HashMap<>(16);
        String methodName = "";
        String varCallMethod = "";
        if (tree.is(Kind.METHOD_INVOCATION)) {
            MethodInvocationTree methodInvocationTree = (MethodInvocationTree) tree;
            if(methodInvocationTree.methodSelect().is(Kind.MEMBER_SELECT)){
                MemberSelectExpressionTree MemberSelectExpressionTree = (MemberSelectExpressionTree) methodInvocationTree.methodSelect();
                methodName = MemberSelectExpressionTree.identifier().toString();
                varCallMethod = MemberSelectExpressionTree.expression().toString();
                if(KEY_SET_METHOD.equals(methodName)){
                    returnMap.put("isKeySetIterated",true);
                }else{
                    returnMap.put("isKeySetIterated",false);
                }
                returnMap.put("varCallMethod",varCallMethod);
            }
        }
        return returnMap;
    }

    private static class ForEachBlockVisitor extends BaseTreeVisitor {

        private String varName;
        private String methodName;
        private List<Tree> issueTrees = new ArrayList<>();

        public ForEachBlockVisitor(String varName,String methodName) {
            this.varName = varName;
            this.methodName = methodName;
        }

        @Override
        public void visitMemberSelectExpression(MemberSelectExpressionTree tree) {
            String expression = ExpressionsHelper.concatenate(tree);
            boolean shouldProcess = expression.equals(varName + methodName);
            if (shouldProcess) {
                issueTrees.add(tree);
            }
        }
    }
}
