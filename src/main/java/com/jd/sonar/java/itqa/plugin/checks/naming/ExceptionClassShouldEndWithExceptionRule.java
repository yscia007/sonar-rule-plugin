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
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;


/**
 * @author: zhangliwei29
 * @date: 2019/1/28
 * @desc: Exception class names must be ended with Exception.
 * */

@Rule(key = "p3c007")
public class ExceptionClassShouldEndWithExceptionRule extends IssuableSubscriptionVisitor {

    private static final String EXCEPTION = "Exception";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        if (! hasSemantic()) {
            return;
        }
        ClassTree classTree = (ClassTree) tree;
        if (hasSuperExceptionClass(classTree) && !hasCorrectNaming(classTree)) {
            reportIssue(classTree.simpleName(), "异常类命名使用 Exception 结尾");
        }
    }

    private static Boolean hasSuperExceptionClass(ClassTree tree) {
        if (tree.superClass() == null) {
            return false;
        }
        return tree.superClass().symbolType().name().endsWith(EXCEPTION);
    }

    private static Boolean hasCorrectNaming(ClassTree tree) {
        if (tree.simpleName() == null) {
            return true;
        }
        return tree.simpleName().name().endsWith(EXCEPTION);
    }
}
