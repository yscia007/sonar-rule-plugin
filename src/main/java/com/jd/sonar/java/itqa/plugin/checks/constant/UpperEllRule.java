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
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;

/**
 * @author: zhangliwei29
 *  @date: 2019/2/14
 * */

@Rule(key = "p3c019")
public class UpperEllRule extends IssuableSubscriptionVisitor {

    private static final String LOWER_CASE_L= "l";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.LONG_LITERAL);
    }

    @Override
    public void visitNode(Tree tree) {
        LiteralTree literalTree = (LiteralTree) tree;
        String value = literalTree.value();
        if (hasIrregularPattern(value) ) {
            reportIssue(literalTree, "long 或者 Long 初始赋值时，使用大写的 L，不能是小写的 l，小写容易跟数字 1 混 淆，造成误解。");
        }
    }

    private static boolean hasIrregularPattern(String literalValue) {
        return literalValue.contains(LOWER_CASE_L);
    }


}
