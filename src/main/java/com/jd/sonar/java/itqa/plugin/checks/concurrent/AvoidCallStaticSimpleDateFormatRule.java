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
package com.jd.sonar.java.itqa.plugin.checks.concurrent;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.java.model.ExpressionUtils;
import org.sonar.java.model.ModifiersUtils;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.*;

/**
 * @author: yangshuo8
 * @date: 2019-02-25 17:16
 * @desc: [Mandatory] SimpleDataFormat is unsafe, do not define it as a static variable.
 *        If have to, lock or DateUtils class must be used.
 */
@Rule(key = "p3c025")
public class AvoidCallStaticSimpleDateFormatRule extends IssuableSubscriptionVisitor {

    private Set<String> simpleDateFormatNames = new HashSet<>();
    private Deque<IdentifierTree> methodStack = new ArrayDeque<>();
    private Deque<Boolean> synchronizedStack = new ArrayDeque<>();
    private static final String QUALIFIED_NAME = "java.text.SimpleDateFormat";
    private static final String FORMAT_METHOD = "format";
    private static final String LOCK_METHOD = "lock";
    private static final String UNLOCK_METHOD = "unlock";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.METHOD, Kind.VARIABLE, Kind.METHOD_INVOCATION, Kind.SYNCHRONIZED_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Kind.METHOD) && isSynchronizedMethod((MethodTree) tree)) {
           synchronizedStack.push(true);
        }
        if (tree.is(Kind.SYNCHRONIZED_STATEMENT)) {
            synchronizedStack.push(true);
            super.visitNode(tree);
        }
        if (tree.is(Kind.VARIABLE)) {
            VariableTree vt = (VariableTree) tree;
            if (isSimpleDateFormatType(vt) && isStatic(vt) && isClassMember(vt)) {
                simpleDateFormatNames.add(vt.simpleName().name());
            }
        }
        if (tree.is(Kind.METHOD_INVOCATION)) {
            MethodInvocationTree mit = (MethodInvocationTree) tree;
            String methodName = ExpressionUtils.methodName(mit).name();
            if (isFormatMethodInvocation(mit) && synchronizedStack.isEmpty()) {
                methodStack.push(ExpressionUtils.methodName(mit));
            } else if (LOCK_METHOD.equals(methodName)) {
                methodStack.push(ExpressionUtils.methodName(mit));
            } else if (UNLOCK_METHOD.equals(methodName)) {
                while (!methodStack.isEmpty()) {
                    IdentifierTree idf = methodStack.pop();
                    if (LOCK_METHOD.equals(idf.name())) {
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void leaveNode(Tree tree) {
        if (!synchronizedStack.isEmpty() && (tree.is(Kind.SYNCHRONIZED_STATEMENT))) {
            synchronizedStack.pop();
        }
        if (!synchronizedStack.isEmpty() && tree.is(Kind.METHOD) && isSynchronizedMethod((MethodTree) tree)) {
            synchronizedStack.pop();
        }
    }

    @Override
    public void leaveFile(JavaFileScannerContext context) {
        while (! methodStack.isEmpty()) {
            IdentifierTree idf = methodStack.pop();
            context.reportIssue(this, idf.parent(), "【simpleDateFormat.format()】可能导致线程安全问题");
        }
        methodStack.clear();
        synchronizedStack.clear();
    }

    private Boolean isSimpleDateFormatType(VariableTree tree) {
        if (tree.type().is(Kind.IDENTIFIER)) {
            Type type = tree.type().symbolType();
            return (QUALIFIED_NAME.equals(type.fullyQualifiedName()));
        }
        return false;
    }

    private Boolean isClassMember(VariableTree tree) {
        return tree.parent() != null && tree.parent().is(Kind.CLASS);
    }

    private Boolean isStatic(VariableTree tree) {
        return ModifiersUtils.hasModifier(tree.modifiers(), Modifier.STATIC);
    }

    private Boolean isSynchronizedMethod(MethodTree tree) {
        return ModifiersUtils.hasModifier(tree.modifiers(), Modifier.SYNCHRONIZED);
    }

    private Boolean isFormatMethodInvocation(MethodInvocationTree tree) {
        String methodName = ExpressionUtils.methodName(tree).name();
        if (FORMAT_METHOD.equals(methodName) && tree.methodSelect().is(Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree mset = (MemberSelectExpressionTree) tree.methodSelect();
            if (mset.expression().is(Kind.IDENTIFIER)) {
                String variableName = ((IdentifierTree) mset.expression()).name();
                String qualifiedName = mset.expression().symbolType().fullyQualifiedName();
                if (QUALIFIED_NAME.equals(qualifiedName) && simpleDateFormatNames.contains(variableName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
