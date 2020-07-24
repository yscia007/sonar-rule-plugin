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
package com.jd.sonar.java.itqa.plugin.checks.constant;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.*;

/**
 * @author: yangshuo8
 * @date: 2019-02-26 15:32
 * @desc: Magic values, except for predefined, are forbidden in coding.
 */
@Rule(key = "p3c035")
public class UndefineMagicConstantRule extends IssuableSubscriptionVisitor {

    private List<LiteralTree> literalTreeList = new ArrayList<>();
    private List<Tree> trees = new ArrayList<>();
    private Deque<Integer> keywordLineStack = new ArrayDeque<>();
    private static final String WHITE_LIST_REGEX = "(0|1|\"\"|0.0|1.0|-1|0L|1L)";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.STRING_LITERAL, Kind.LONG_LITERAL, Kind.DOUBLE_LITERAL, Kind.FLOAT_LITERAL, Kind.INT_LITERAL,
                Kind.IF_STATEMENT, Kind.FOR_STATEMENT, Kind.WHILE_STATEMENT);
    }

    @Override
    public void visitNode(Tree tree) {
        if (isLiteralTree(tree)) {
            LiteralTree literalTree = (LiteralTree) tree;
            trees.add(literalTree.parent());
            if (!isInWhiteList(literalTree) && !isInInitializer(literalTree) && isLiteralInKeywordBeginLine(literalTree)) {
                literalTreeList.add(literalTree);
            }
        } else if (tree.is(Kind.IF_STATEMENT)) {
            IfStatementTree ifst = (IfStatementTree) tree;
            keywordLineStack.push(ifst.ifKeyword().line());
        } else if (tree.is(Kind.FOR_STATEMENT)) {
            ForStatementTree fst = (ForStatementTree) tree;
            keywordLineStack.push(fst.forKeyword().line());
        } else if (tree.is(Kind.WHILE_STATEMENT)) {
            WhileStatementTree wst = (WhileStatementTree) tree;
            keywordLineStack.push(wst.whileKeyword().line());
        }
    }

    @Override
    public void leaveNode(Tree tree) {
        if (!keywordLineStack.isEmpty() && hasConditonTree(tree)) {
            keywordLineStack.pop();
        }
     }

    @Override
    public void leaveFile(JavaFileScannerContext context) {
        if (! literalTreeList.isEmpty()) {
            Set<String> literalSet = new HashSet<>();
            List<LiteralTree> issueTreeList = new ArrayList<>();
            for (LiteralTree tree : literalTreeList) {
                if (! literalSet.contains(tree.value())) {
                    literalSet.add(tree.value());
                    issueTreeList.add(tree);
                }
            }
            for (LiteralTree tree : issueTreeList) {
                context.reportIssue(this, tree, "这是一个魔法值" + tree.value());
            }
        }
        literalTreeList.clear();
        keywordLineStack.clear();
    }

    private Boolean isLiteralTree(Tree tree) {
        return tree.is(Kind.STRING_LITERAL) || tree.is(Kind.INT_LITERAL) || tree.is(Kind.LONG_LITERAL) ||
                tree.is(Kind.FLOAT_LITERAL) || tree.is(Kind.DOUBLE_LITERAL);
    }

    private Boolean isInWhiteList(LiteralTree tree) {
        return tree.value().matches(WHITE_LIST_REGEX);
    }

    private Boolean isInInitializer(LiteralTree tree) {
        return tree.parent() != null && tree.parent().is(Kind.VARIABLE);
    }

    private Boolean hasConditonTree(Tree tree) {
        return tree.is(Kind.IF_STATEMENT) || tree.is(Kind.WHILE_STATEMENT) || tree.is(Kind.FOR_STATEMENT);
    }

    private Boolean isLiteralInKeywordBeginLine(LiteralTree tree) {
        return !keywordLineStack.isEmpty() && tree.token().line() == keywordLineStack.pop();
    }
}
