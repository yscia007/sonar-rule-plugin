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
package com.jd.sonar.java.itqa.plugin.checks.comment;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.SyntaxTrivia;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: chenxiaoli
 * @date: 2019-02-25 17:16
 * @desc: [Mandatory] Every class should include information of author(s) and date.
 */

@Rule(key = "p3c045")
public class ClassMustHaveAuthorRule  extends IssuableSubscriptionVisitor {

    private static final Pattern AUTHOR_PATTERN = Pattern.compile(".*@author.*",
            Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    private static final String MESSAGE = "缺少包含@author的注释信息";

   @Override
    public List<Kind> nodesToVisit() {
       return ImmutableList.of(Kind.CLASS, Kind.INTERFACE, Kind.ENUM);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;
        Tree parent = classTree.parent();

        if (parent != null && !parent.is(Kind.COMPILATION_UNIT)) {
            return;
        }

        if (classTree.modifiers().firstToken() == null) {
            reportIssue(classTree.simpleName(), MESSAGE);
            return;
        }
        List<SyntaxTrivia> syntaxTrivias = classTree.modifiers().firstToken().trivias();
        if (syntaxTrivias.isEmpty()) {
            reportIssue(classTree.simpleName(), MESSAGE);
            return;
        }

        boolean hasAuthorComment = syntaxTrivias.stream()
                .anyMatch(t -> AUTHOR_PATTERN.matcher(t.comment()).matches());

        if (! hasAuthorComment) {
            reportIssue(classTree.simpleName(), MESSAGE);
        }
    }
}
