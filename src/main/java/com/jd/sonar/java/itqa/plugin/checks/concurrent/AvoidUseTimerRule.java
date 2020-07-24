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
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019-02-26 14:09
 * @desc: Run multiple TimeTask by using ScheduledExecutorService rather than Timer
 *        because Timer will kill all running threads in case of failing to catch exception.
 */
@Rule(key = "p3c026")
public class AvoidUseTimerRule extends AbstractMethodDetection {

    private static final String QUALIFIED_NAME = "java.util.Timer";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.NEW_CLASS, Kind.VARIABLE);
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Kind.VARIABLE)) {
            VariableTree variableTree = (VariableTree) tree;
            if (isTimerType(variableTree) && variableTree.initializer() == null) {
                reportIssue(variableTree.simpleName(), "使用ScheduledExecutorService代替Timer吧");
            }
        } else if (tree.is(Kind.NEW_CLASS)) {
            super.visitNode(tree);
        }
    }

    @Override
    protected void onConstructorFound(NewClassTree newClassTree) {
        if (newClassTree.identifier().is(Kind.IDENTIFIER)) {
            reportIssue(newClassTree.identifier(), "使用ScheduledExecutorService代替Timer吧");
        }
    }

    @Override
    protected List<MethodMatcher> getMethodInvocationMatchers() {
        return ImmutableList.of(
                MethodMatcher.create().typeDefinition(QUALIFIED_NAME).name("<init>").withAnyParameters()
        );
    }

    private Boolean isTimerType(VariableTree tree) {
        if (tree.type().is(Kind.IDENTIFIER)) {
            Type type = tree.type().symbolType();
            return QUALIFIED_NAME.equals(type.fullyQualifiedName());
        }
        return false;
    }
}
