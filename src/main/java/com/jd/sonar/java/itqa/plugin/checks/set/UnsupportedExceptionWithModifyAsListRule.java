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
import org.sonar.java.checks.helpers.ExpressionsHelper;
import org.sonar.java.model.ExpressionUtils;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: yangshuo27
 *  @date: 2019/2/19
 * */

@Rule(key = "p3c018")
public class UnsupportedExceptionWithModifyAsListRule extends BaseTreeVisitor implements JavaFileScanner {

    private static final String ASLIST_EXPRESSION = "Arrays.asList";
    private static final String ASLIST = "asList";
    private static final String LIST_METHOD_REGEX = "(add|remove|clear)";
    private Deque<String> asListStack = new ArrayDeque<>();
    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitMethod(MethodTree tree) {
        scan(tree.block());
        asListStack.clear();
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        String name = ExpressionUtils.methodName(tree).name();
        boolean shouldRecord = ASLIST.equals(name);
        if (shouldRecord) {
            recordAsList(tree);
        }
        boolean shouldCheck = !asListStack.isEmpty() && name.matches(LIST_METHOD_REGEX);
        if (shouldCheck && hasModifyAsList(tree)) {
            context.reportIssue(this, tree, "这里使用\"" + name + "\"可能会导致UnsupportedOperationException");
        }
    }

    private void recordAsList(MethodInvocationTree tree) {
        if (tree.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree mset = (MemberSelectExpressionTree) tree.methodSelect();
            String expression = ExpressionsHelper.concatenate(mset);
            boolean shouldRecord = ASLIST_EXPRESSION.equals(expression) && tree.parent().is(Tree.Kind.VARIABLE);
            if (shouldRecord) {
                VariableTree variableTree = (VariableTree) tree.parent();
                asListStack.push(variableTree.simpleName().name());
            }
        }
    }

    private Boolean hasModifyAsList(MethodInvocationTree tree) {
        if (asListStack.isEmpty()) {
            return false;
        }
        if (tree.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree mset = (MemberSelectExpressionTree) tree.methodSelect();
            if (mset.expression().is(Tree.Kind.IDENTIFIER)) {
                String listName = ((IdentifierTree) mset.expression()).name();
                return asListStack.contains(listName);
            }
        }
        return false;
    }
}
