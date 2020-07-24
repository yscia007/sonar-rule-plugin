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
package com.jd.sonar.java.itqa.plugin.checks;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019-04-18 10:46
 * @desc: 该规则在sonar-java规则：squid:MethodCyclomaticComplexity 基础上进行了修改，主要为：
 *        1）message、规则描述翻译为中文（方便国内开发者）；
 *        2）代码逻辑优化；
 */
@Rule(key = "QAS001")
public class MethodCyclomaticComplexityCheck extends IssuableSubscriptionVisitor {

    private static final int DEFAULT_MAX = 10;
    private static final String EQUALS = "equals";
    private static final String HASH_CODE = "hashCode";

    @RuleProperty(
            key = "Threshold",
            description = "The maximum authorized complexity.",
            defaultValue = "" + DEFAULT_MAX)
    private int max = DEFAULT_MAX;

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.METHOD, Tree.Kind.CONSTRUCTOR);
    }

    @Override
    public void visitNode(Tree tree) {
        MethodTree methodTree = (MethodTree) tree;
        if (isExcluded(methodTree)) {
            return;
        }
        List<Tree> complexity = context.getComplexityNodes(methodTree);
        int size = complexity.size();
        // 圈复杂度不超过设定的阈值时忽略；
        if (size < max + 1) {
            return;
        }
        List<JavaFileScannerContext.Location> flow = new ArrayList<>();
        for (Tree element : complexity) {
            flow.add(new JavaFileScannerContext.Location("+1", element));
        }
        reportIssue(
                methodTree.simpleName(),
                "方法 \"" + methodTree.simpleName().name() + "\" 的圈复杂度为 " + size + " ，超过了允许的最大值 " + max + " ，请降低方法的圈复杂度。",
                flow,
                size - max);

    }

    private static boolean isExcluded(MethodTree methodTree) {
        String name = methodTree.simpleName().name();
        if (EQUALS.equals(name)) {
            return methodTree.parameters().size() == 1;
        } else if (HASH_CODE.equals(name)) {
            return methodTree.parameters().isEmpty();
        }
        return false;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
