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
import org.sonar.plugins.java.api.tree.EnumConstantTree;
import org.sonar.plugins.java.api.tree.SyntaxTrivia;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author: chenxiaoli
 * @date: 2019-02-25 17:16
 * @desc: [Mandatory] All enumeration type fields should be commented as Javadoc style.
 */

@Rule(key = "p3c047")
public class EnumConstantsMustHaveCommentRule  extends IssuableSubscriptionVisitor {

    private static final Pattern JAVADOC_PATTERN = Pattern.compile("\\/\\*(\\s|.)*?\\*\\/",
            Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.ENUM);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;
        List<EnumConstantTree> enumConstantTrees = classTree.members().stream()
                .filter(t -> t.is(Kind.ENUM_CONSTANT))
                .map(t -> (EnumConstantTree) t)
                .collect(Collectors.toList());
        if (enumConstantTrees.isEmpty()){
            return;
        }
        boolean isEnumConstantHasJavadoc = enumConstantTrees.stream()
                .anyMatch(t -> hasJavadocComment(t));
        if (!isEnumConstantHasJavadoc) {
            reportIssue(enumConstantTrees.get(0).simpleName(), "枚举类型字段必须要有注释");
        }
    }

    private Boolean hasJavadocComment(EnumConstantTree tree) {

        List<SyntaxTrivia> trivias = tree.simpleName().identifierToken().trivias();
        if (trivias.isEmpty()) {
            return false;
        }
        return trivias.stream()
                .anyMatch(t -> JAVADOC_PATTERN.matcher(t.comment()).matches());
    }
}
