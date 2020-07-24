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
package com.jd.sonar.java.itqa.plugin.checks.security;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.java.model.ExpressionUtils;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019-04-18 17:26
 * @desc: Insecure deserialization check.
 */
@Rule(key = "QA127")
public class JavaIoDeserializationMethodCheck extends AbstractMethodDetection {

    private static final String JAVA_IO_OBJECT_INPUT_STREAM = "java.io.ObjectInputStream";
    private Deque<String> objectInputStreamStack = new ArrayDeque<>();

    @Override
    protected List<MethodMatcher> getMethodInvocationMatchers() {
        return ImmutableList.of(
                MethodMatcher.create().typeDefinition(JAVA_IO_OBJECT_INPUT_STREAM).name("<init>").withAnyParameters(),
                MethodMatcher.create().typeDefinition(JAVA_IO_OBJECT_INPUT_STREAM).name("readObject").withAnyParameters()
        );
    }

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.METHOD, Kind.NEW_CLASS, Kind.METHOD_INVOCATION);
    }

    @Override
    protected void onConstructorFound(NewClassTree newClassTree) {
        if (newClassTree.parent().is(Kind.VARIABLE)) {
            VariableTree variableTree = (VariableTree) newClassTree.parent();
            objectInputStreamStack.push(variableTree.simpleName().name());
        }
    }

    @Override
    protected void onMethodInvocationFound(MethodInvocationTree tree) {
        if (!tree.methodSelect().is(Kind.MEMBER_SELECT)) {
            return;
        }
        MemberSelectExpressionTree mset = (MemberSelectExpressionTree) tree.methodSelect();
        if (hasInsecureDeserialization(mset)) {
            reportIssue(tree, "不安全的反序列化操作 \"" + ExpressionUtils.methodName(tree).name() + "\"，需要进行安全加固");
        }
    }

    @Override
    public void leaveNode(Tree tree) {
        if (tree.is(Kind.METHOD)) {
            objectInputStreamStack.clear();
        }
    }

    private Boolean hasInsecureDeserialization(MemberSelectExpressionTree tree) {
        if (tree.expression().is(Kind.IDENTIFIER)) {
            IdentifierTree idf = (IdentifierTree) tree.expression();
            return  !objectInputStreamStack.isEmpty() && objectInputStreamStack.contains(idf.name());
        } else if (tree.expression().is(Kind.NEW_CLASS)) {
            NewClassTree nct = (NewClassTree) tree.expression();
            return JAVA_IO_OBJECT_INPUT_STREAM.equals(nct.symbolType().fullyQualifiedName());
        }
        return false;
    }
}
