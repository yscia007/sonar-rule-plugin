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
package com.jd.sonar.java.itqa.plugin.checks.oop;


import com.google.common.collect.ImmutableList;
import com.jd.sonar.java.itqa.plugin.checks.helpers.PojoUtils;
import org.sonar.check.Rule;
import org.sonar.java.model.ModifiersUtils;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: zhangliwei29
 * @date: 2019/2/26
 * @desc: Members of a POJO class must be wrapper classes.
 * */

@Rule(key = "p3c038")
public class PojoMustUsePrimitiveFieldRule extends IssuableSubscriptionVisitor {

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;
        if (PojoUtils.isPojo(classTree)) {
            List<VariableTree> variableTrees = classTree.members().stream()
                    .filter(t -> t.is(Kind.VARIABLE))
                    .map(t -> (VariableTree) t)
                    .collect(Collectors.toList());
            checkVariableType(variableTrees);
        }
    }

    private void checkVariableType(List<VariableTree> variableTrees) {
        if (variableTrees.isEmpty()) {
            return;
        }
        for (VariableTree tree : variableTrees) {
            if (shouldProcess(tree) && tree.type().symbolType().isPrimitive()) {
                reportIssue(tree.simpleName(), " POJO 类属性必须使用包装数据类型");
            }
        }
    }

    private Boolean shouldProcess(VariableTree tree) {
        ModifiersTree modifiersTree = tree.modifiers();
        return !ModifiersUtils.hasModifier(modifiersTree, Modifier.PUBLIC) &&
                !ModifiersUtils.hasModifier(modifiersTree, Modifier.STATIC) &&
                !ModifiersUtils.hasModifier(modifiersTree, Modifier.FINAL) &&
                !ModifiersUtils.hasModifier(modifiersTree, Modifier.VOLATILE);
    }
}
