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
package com.jd.sonar.java.itqa.plugin.checks.naming;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.java.model.ModifiersUtils;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019-02-13 16:07
 * @desc: Constant variable names should be written in upper characters separated by underscores. These names
 * should be semantically complete and clear.
 */

@Rule(key = "p3c006")
public class ConstantFieldShouldBeUpperCaseRule extends IssuableSubscriptionVisitor {

    private static final String WHITE_LIST = "serialVersionUID";
    private static final String LOG_REGEX = "(Log|Logger)";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.VARIABLE);
    }

    @Override
    public void visitNode(Tree tree) {
        VariableTree variableTree = (VariableTree) tree;
        if (isStatic(variableTree) && isFinal(variableTree) && !hasCorrectNaming(variableTree)) {
            reportIssue(variableTree.simpleName(), "常量\"" + variableTree.simpleName().name() + "\"命名应全部大写并以下划线分隔");
        }
    }

    private static Boolean hasCorrectNaming(VariableTree tree) {
        String constantName = tree.simpleName().name();
        return constantName.equals(constantName.toUpperCase()) ||
                isInWhiteList(constantName) ||
                isLogOrLoggerType(tree);
    }

    private static Boolean isStatic(VariableTree variableTree) {
        return ModifiersUtils.hasModifier(variableTree.modifiers(), Modifier.STATIC);
    }

    private static Boolean isFinal(VariableTree variableTree) {
        return ModifiersUtils.hasModifier(variableTree.modifiers(), Modifier.FINAL);
    }

    private static Boolean isInWhiteList(String constantName) {
        return WHITE_LIST.contains(constantName);
    }

    private static Boolean isLogOrLoggerType(VariableTree variableTree) {
        Type type = variableTree.type().symbolType();
        String typeName = "";
        if (type.isPrimitive()) {
            typeName = variableTree.type().symbolType().name();
        } else if (variableTree.type().is(Kind.IDENTIFIER)){
            typeName = ((IdentifierTree) variableTree.type()).name();
        } else if (variableTree.type().is(Kind.PARAMETERIZED_TYPE)) {
            ParameterizedTypeTree ptt = (ParameterizedTypeTree) variableTree.type();
            if (ptt.type().is(Kind.IDENTIFIER)) {
                typeName = ((IdentifierTree) ptt.type()).name();
            }
        }
        return typeName.matches(LOG_REGEX);
    }
}
