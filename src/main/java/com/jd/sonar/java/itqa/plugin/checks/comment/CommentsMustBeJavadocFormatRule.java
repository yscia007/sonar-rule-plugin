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

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: xuxiao200
 * @date: 2019/3/4 15:17
 * @description: [Mandatory] Javadoc should be used for classes, class variables and methods.
 * The format should be '\/** comment *\/', rather than '// xxx'.
*/

@Rule(key = "p3c043")
public class CommentsMustBeJavadocFormatRule extends BaseTreeVisitor implements JavaFileScanner {

    private static final Pattern COMMENT_PATTERN = Pattern.compile("\\/\\*\\*[\\w\\W]*?\\*\\/",
            Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        scan(tree.modifiers());
        scan(tree.simpleName());
        scan(tree.members());
        checkCommentFormat(tree);
    }

    @Override
    public void visitVariable(VariableTree tree) {
        scan(tree.modifiers());
        scan(tree.simpleName());
        checkCommentFormat(tree);
    }

    @Override
    public void visitMethod(MethodTree tree) {
        scan(tree.modifiers());
        scan(tree.simpleName());
        checkCommentFormat(tree);
    }

    @Override
    public void visitEnumConstant(EnumConstantTree tree) {
        scan(tree.modifiers());
        scan(tree.simpleName());
        checkCommentFormat(tree);
    }

    private void checkCommentFormat(ClassTree tree) {
        SyntaxToken token = tree.modifiers().firstToken();
        if (token == null) {
            return;
        }
        List<SyntaxTrivia> trivias = tree.modifiers().firstToken().trivias();
        if (! hasCorrectCommentFormat(trivias)) {
            context.reportIssue(this, tree.simpleName(), "注释应使用/**内容*/格式");
        }
    }

    private void checkCommentFormat(MethodTree tree) {
        SyntaxToken token = tree.modifiers().firstToken();
        if (token == null) {
            return;
        }
        List<SyntaxTrivia> trivias = tree.modifiers().firstToken().trivias();
        if (! hasCorrectCommentFormat(trivias)) {
            context.reportIssue(this, tree.simpleName(), "注释应使用/**内容*/格式");
        }
    }

    private void checkCommentFormat(VariableTree tree) {
        SyntaxToken token = tree.modifiers().firstToken();
        if (token == null) {
            return;
        }
        List<SyntaxTrivia> trivias = tree.modifiers().firstToken().trivias();
        if (! hasCorrectCommentFormat(trivias)) {
            context.reportIssue(this, tree.simpleName(), "注释应使用/**内容*/格式");
        }
    }

    private void checkCommentFormat(EnumConstantTree tree) {
        SyntaxToken token = tree.simpleName().firstToken();
        if (token == null) {
            return;
        }
        List<SyntaxTrivia> trivias = tree.simpleName().firstToken().trivias();
        if (! hasCorrectCommentFormat(trivias)) {
            context.reportIssue(this, tree.simpleName(), "注释应使用/**内容*/格式");
        }
    }

    private Boolean hasCorrectCommentFormat(List<SyntaxTrivia> trivias) {
        if (trivias.isEmpty()) {
            return true;
        }
        boolean isCorrectComment = false;
        for (SyntaxTrivia trivia : trivias) {
            if (COMMENT_PATTERN.matcher(trivia.comment()).matches()) {
                isCorrectComment = true;
            }
        }
        return isCorrectComment;
    }
}
