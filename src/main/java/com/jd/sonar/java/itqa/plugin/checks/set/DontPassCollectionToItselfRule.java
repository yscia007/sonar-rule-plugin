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

import org.sonar.check.Rule;
import org.sonar.java.model.ExpressionUtils;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

import java.util.*;

/**
 * Created by weiqiao on 2020/3/20.
 * @desc：Don't pass collection as parameter to itself.
 * now:containsAll & removeAll --> other to be discovered
 */
@Rule(key = "QAS005")
public class DontPassCollectionToItselfRule extends BaseTreeVisitor implements JavaFileScanner {

    private static final String MESSAGE = "不要把集合对象传给自己";
//    private static final List<String> METHOD_LIST = Arrays.asList("containsAll", "removeAll");
    private static final Set<String> METHOD_SET = new HashSet<String>(){{
        add("containsAll");
        add("removeAll");
    }};
    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        String name  = ExpressionUtils.methodName(tree).name();
        String varCallMethod = getVarCallMethod(tree);
        String argument = getArgs(tree);
        Boolean isVarEqualsToArg = false;
        if(!"".equals(varCallMethod) && argument.equals(varCallMethod)){
            isVarEqualsToArg = true;
        }
        if (METHOD_SET.contains(name) && isVarEqualsToArg) {
            context.reportIssue(this, tree, MESSAGE);
        }
    }

    private String getVarCallMethod(MethodInvocationTree tree) {
        String listName = "";
        if (tree.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree memberSelectExpressionTree = (MemberSelectExpressionTree) tree.methodSelect();
            if (memberSelectExpressionTree.expression().is(Tree.Kind.IDENTIFIER)) {
                listName = ((IdentifierTree) memberSelectExpressionTree.expression()).name();
            }
        }
        return listName;
    }

    private String getArgs(MethodInvocationTree tree) {
        String argName = "";
        if (tree.arguments().is(Tree.Kind.ARGUMENTS)) {
            Arguments arguments = tree.arguments();
            if(!arguments.isEmpty() && arguments.size()==1){
                ExpressionTree expressionTree = arguments.get(0);
                argName = expressionTree.toString();
            }
        }
        return argName;
    }
}
