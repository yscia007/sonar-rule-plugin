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

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: zhangliwei29
 *  @date: 2019/2/13
 * */

@Rule(key = "p3c014")
public class ConcurrentExceptionWithModifyOriginSubListRule extends BaseTreeVisitor implements JavaFileScanner {

    private static final String MODIFY_METHOD_REGEX = "(add|remove|clear)";
    private static final String SUBLIST = "subList";
    private Deque<String> originListStack = new ArrayDeque<>();
    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitMethod(MethodTree tree) {
        scan(tree.block());
        originListStack.clear();
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        String name = ExpressionUtils.methodName(tree).name();
        boolean shouldRecord = SUBLIST.equals(name);
        if (shouldRecord) {
            recordOriginList(tree);
        }
        boolean shouldCheck = !originListStack.isEmpty() && name.matches(MODIFY_METHOD_REGEX);
        if (shouldCheck && hasModifyOriginList(tree)) {
            context.reportIssue(this, tree, "这里使用\"" + name + "\"可能会导致ConcurrentModificationException");
        }
    }

    private void recordOriginList(MethodInvocationTree tree) {
        if (tree.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree mset = (MemberSelectExpressionTree) tree.methodSelect();
            if (mset.expression().is(Tree.Kind.IDENTIFIER)) {
                String originListName = ((IdentifierTree) mset.expression()).name();
                originListStack.push(originListName);
            }
        }
    }

    private Boolean hasModifyOriginList(MethodInvocationTree tree) {
        if (originListStack.isEmpty()) {
            return false;
        }
        if (tree.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree mset = (MemberSelectExpressionTree) tree.methodSelect();
            if (mset.expression().is(Tree.Kind.IDENTIFIER)) {
                String name = ((IdentifierTree) mset.expression()).name();
                return originListStack.contains(name);
            }
        }
        return false;
    }
}