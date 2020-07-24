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
package com.jd.sonar.java.itqa.plugin.checks.helpers;

import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author: yangshuo8
 * @date: 2019-02-28 10:35
 * @desc: description
 */
public class AssignmentExpressionUtils {

    private AssignmentExpressionUtils() {}

    public static String concatenate(@Nullable AssignmentExpressionTree tree) {
        if (tree == null) {
            return "";
        }
        Deque<String> pieces = new ArrayDeque<>();
        AssignmentExpressionTree aet = tree;
        if (aet.variable().is(Kind.IDENTIFIER)) {
            pieces.push(((IdentifierTree) aet.variable()).name());
            pieces.push(aet.operatorToken().text());
        }
        if (isLiteralTree(aet.expression())) {
            pieces.push(((LiteralTree) aet.expression()).value());
        }
        StringBuilder sb = new StringBuilder();
        while (!pieces.isEmpty()) {
            sb.append(pieces.pollLast());
        }
        return sb.toString();
    }

    private static Boolean isLiteralTree(ExpressionTree tree) {
        return tree.is(Kind.BOOLEAN_LITERAL) || tree.is(Kind.STRING_LITERAL) || tree.is(Kind.INT_LITERAL) || tree.is(Kind.LONG_LITERAL) ||
                tree.is(Kind.FLOAT_LITERAL) || tree.is(Kind.DOUBLE_LITERAL) || tree.is(Kind.NULL_LITERAL);
    }
}
