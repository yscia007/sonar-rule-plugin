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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by weiqiao on 2020/4/2.
 * Escape characters in split should be paid special attention.
 */
@Rule(key = "QAS007")
public class SplitShouldBePaidAttentionRule extends IssuableSubscriptionVisitor{

    private static String MESSAGE = "使用split方法时请注意转义字符！";
    private static String METHOD_NAME = "split";
    private static final Set<String> ESCAPE_CHARACTER_SET = new HashSet<String>(){{
          add("^");   add("$");    add(".");
    }};
    private static String BACKSLASH = "\\\\";
    private static String BLACK = "";


    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {

        if(tree.is(Tree.Kind.METHOD_INVOCATION)){
            MethodInvocationTree methodInvocationTree = (MethodInvocationTree) tree;
            if(methodInvocationTree.methodSelect().is(Tree.Kind.MEMBER_SELECT)){
                MemberSelectExpressionTree memberSelectExpressionTree = (MemberSelectExpressionTree) methodInvocationTree.methodSelect();
                if(METHOD_NAME.equals(memberSelectExpressionTree.identifier().name())){
                    Arguments arguments = methodInvocationTree.arguments();
                    if(arguments.get(0).is(Tree.Kind.STRING_LITERAL)){
                        LiteralTree literalTree = (LiteralTree) arguments.get(0);
                        String argValue = literalTree.token().text();
                        if(!isValidPara(argValue)){
                            reportIssue(literalTree, MESSAGE);
                        }
                    }else if(arguments.get(0).is(Tree.Kind.IDENTIFIER)){
                        IdentifierTree identifierTree = (IdentifierTree)arguments.get(0);
                        VariableTree variableTree = (VariableTree)identifierTree.symbol().declaration();
                        if(!(variableTree.initializer()==null)){
                            String argValue = variableTree.initializer().firstToken().text();
                            if(!isValidPara(argValue)){
                                reportIssue(identifierTree, MESSAGE);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取参数中的value值；空串数大于\\数  --> 说明有非法的"|"字符
     * 如果最末尾存在\\则将空串数+1
    * */
    private Boolean isValidPara(String para){

        para = para.substring(1, para.length()-1);
        String[] paraList = para.split("\\|");

        int blankNum = 0;
        int backslashNum = 0;
        for(int i=0;i<paraList.length;i++){
            String character = paraList[i];
            if(ESCAPE_CHARACTER_SET.contains(character)){
                return false;
            }else if(BLACK.equals(character)){
                blankNum ++;
            }else if(BACKSLASH.equals(character)){
                backslashNum ++;
            }
        }
        if(BACKSLASH.equals(paraList[paraList.length-1])){
            blankNum ++;
        }

        if(blankNum > backslashNum){
            return false;
        }

        return true;
    }
}
