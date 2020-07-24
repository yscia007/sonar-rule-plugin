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
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019/3/16
 * @desc:
 */

@Rule(key = "p3c010")
public class ArrayNamingShouldHaveBracketRule extends IssuableSubscriptionVisitor {

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.VARIABLE);
    }

    @Override
    public void visitNode(Tree tree) {
        VariableTree variableTree = (VariableTree) tree;
        TypeTree typeTree = variableTree.type();
        if (!typeTree.is(Tree.Kind.ARRAY_TYPE)) {
           return;
        }
        ArrayTypeTree arrayTypeTree = (ArrayTypeTree) typeTree;
        if (arrayTypeTree.type().is(Tree.Kind.IDENTIFIER)) {
            IdentifierTree idf = (IdentifierTree) arrayTypeTree.type();
            if (arrayTypeTree.openBracketToken() != null && !isBracketInCorrectPosition(idf, arrayTypeTree.openBracketToken(), variableTree)) {
                reportIssue(idf, "应将'[]'放在数组类型名称 '" + idf.name() + "' 之后");
            }
        }
    }

    private static Boolean isBracketInCorrectPosition(IdentifierTree tree, SyntaxToken openBracketToken, VariableTree variableTree) {
        SyntaxToken identifierToken = tree.identifierToken();
        int typeColumn = identifierToken.column();
        int openBracketPosition  = openBracketToken.column();
        int variableColumn = variableTree.simpleName().identifierToken().column();
        return (identifierToken.line() == openBracketToken.line()) &&
                openBracketPosition > typeColumn &&
                openBracketPosition < variableColumn;
    }
}
