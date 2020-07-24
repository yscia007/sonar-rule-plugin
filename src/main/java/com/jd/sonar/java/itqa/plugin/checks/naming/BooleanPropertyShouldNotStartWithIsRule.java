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
import com.jd.sonar.java.itqa.plugin.checks.helpers.PojoUtils;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019/3/18 21:20
 * @desc: Do not add 'is' as prefix while defining Boolean variable, since it may cause a serialization exception
 *  in some Java Frameworks.
 */
@Rule(key = "p3c033")
public class BooleanPropertyShouldNotStartWithIsRule extends IssuableSubscriptionVisitor {

    private static final String NAME_PREFIX = "is";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;
        if (! PojoUtils.isPojo(classTree)) {
            return;
        }
        for (Tree member : classTree.members()) {
            if (member.is(Kind.VARIABLE)) {
                VariableTree variableTree = (VariableTree) member;
                boolean shouldProcess = variableTree.type().symbolType().isPrimitive(Type.Primitives.BOOLEAN) &&
                        variableTree.simpleName().name().startsWith(NAME_PREFIX);
                if (shouldProcess) {
                    reportIssue(variableTree.simpleName(), "POJO 类中布尔类型的变量命名不能以'is'为前缀");
                }
            }
        }
    }
}
