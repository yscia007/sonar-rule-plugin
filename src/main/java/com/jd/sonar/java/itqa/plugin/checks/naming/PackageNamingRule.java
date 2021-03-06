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

import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.java.model.PackageUtils;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;

import java.util.regex.Pattern;

/**
 * @author: zhangliwei29
 *  @date: 2019/1/28
 * */


@Rule(key = "p3c008")
public class PackageNamingRule extends BaseTreeVisitor implements JavaFileScanner {

    private static final String DEFAULT_FORMAT = "^[a-z_]+(\\.[a-z_][a-z0-9_]*)*$";
    private static final String DOT_SEPARATOR = ".";

    @RuleProperty(
            key = "format",
            description = "包名统一使用小写，点分隔符之间有且仅有一个自然语义的英语单词。包名统一使用单数形式，但是类名如果有复数含义，类名可以使用复数形式。",
            defaultValue = DEFAULT_FORMAT)
    public String format = DEFAULT_FORMAT;

    private Pattern pattern = null;
    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        if (pattern == null) {
            pattern = Pattern.compile(format, Pattern.DOTALL);
        }
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitCompilationUnit(CompilationUnitTree tree) {
        if (tree.packageDeclaration() == null) {
            return;
        }
        String name = PackageUtils.packageName(tree.packageDeclaration(), DOT_SEPARATOR);
        if (!pattern.matcher(name).matches()) {
            context.reportIssue(this, tree.packageDeclaration().packageName(), "包名统一使用小写，点分隔符之间有且仅有一个自然语义的英语单词。包名统一使用单数形式，但是类名如果有复数含义，类名可以使用复数形式。");
        }
    }
}
