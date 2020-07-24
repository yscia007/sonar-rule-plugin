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
import org.sonar.java.ast.visitors.CognitiveComplexityVisitor;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;

import static org.sonar.plugins.java.api.tree.Tree.Kind.CONSTRUCTOR;
import static org.sonar.plugins.java.api.tree.Tree.Kind.METHOD;

/**
 * @author: yangshuo8
 * @date: 2019-04-18 14:28
 * @desc: 该规则在sonar-java规则：squid:CognitiveComplexityMethodCheck 基础上进行了修改，主要为：
 *      1）message、规则描述翻译为中文（方便国内开发者）；
 */
@Rule(key = "QAS002")
public class MethodCognitiveComplexityCheck extends IssuableSubscriptionVisitor {

    private static final int DEFAULT_MAX = 15;

    @RuleProperty(
            key = "Threshold",
            description = "The maximum authorized complexity.",
            defaultValue = "" + DEFAULT_MAX)
    private int max = DEFAULT_MAX;

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(METHOD, CONSTRUCTOR);
    }

    @Override
    public void visitNode(Tree tree) {
        MethodTree method = (MethodTree) tree;
        CognitiveComplexityVisitor.Result result = CognitiveComplexityVisitor.methodComplexity(method);
        int total = result.complexity;
        if (total > max) {
            reportIssue(method.simpleName(),
                    "请重构方法 \"" + method.simpleName().name() + "\" 并将方法的认知复杂度从 " + total + " 降低为允许的最大值 " + max + "。", result.locations, total - max);
        }
    }

    public void setMax(int max) {
        this.max = max;
    }
}
