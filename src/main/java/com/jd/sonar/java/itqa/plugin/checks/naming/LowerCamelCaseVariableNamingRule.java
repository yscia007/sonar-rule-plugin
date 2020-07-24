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
import org.sonar.java.resolve.JavaType;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.*;

import java.util.regex.Pattern;

/**
 * @author: lina51
 *  @date: 2019/2/20
 * */


@Rule(key = "p3c031")
public class LowerCamelCaseVariableNamingRule extends BaseTreeVisitor implements JavaFileScanner {

    private static final String DEFAULT_FORMAT = "^[a-z|$][a-z0-9]*([A-Z][a-z0-9]*)*(DO|DTO|VO|DAO)?$";

    @RuleProperty(
            key = "format",
            description = "Regular expression used to check the names against.",
            defaultValue = "" + DEFAULT_FORMAT)
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
    public void visitClass(ClassTree tree) {
        for (Tree member : tree.members()) {
            if (member.is(Tree.Kind.VARIABLE)) {
                // skip check of field
                scan(((VariableTree) member).initializer());
            } else{
                scan(member);
            }
        }
    }

    @Override
    public void visitForStatement(ForStatementTree tree) {
        scan(tree.statement());
    }

    @Override
    public void visitForEachStatement(ForEachStatement tree) {
        scan(tree.statement());
    }

    @Override
    public void visitCatch(CatchTree tree) {
        VariableTree parameter = tree.parameter();
        if (parameter.simpleName().name().length() > 1) {
            scan(parameter);
        }
        scan(tree.block());
    }

    @Override
    public void visitVariable(VariableTree tree) {
        if (!hasCorrectNaming(tree.simpleName()) && !isLocalConstant(tree)) {
            context.reportIssue(this, tree.simpleName(), "变量名或参数名 \"" + tree.simpleName().name() + "\" 命名统一使用 lowerCamelCase 风格");
        }
        super.visitVariable(tree);
    }

    @Override
    public void visitMethod(MethodTree tree) {
        boolean shouldProcess = !hasCorrectNaming(tree.simpleName()) && isNotOverriden(tree) && !tree.is(Tree.Kind.CONSTRUCTOR);
        if (shouldProcess) {
            context.reportIssue(this, tree.simpleName(), "方法名 \"" + tree.simpleName().name() + "\" 命名统一使用 lowerCamelCase 风格");
        }
        super.visitMethod(tree);
    }

    private Boolean hasCorrectNaming(IdentifierTree tree) {
        return pattern.matcher(tree.name()).matches();
    }

    private Boolean isLocalConstant(VariableTree tree) {
        return context.getSemanticModel() != null && isConstantType(tree.symbol().type()) && tree.symbol().isFinal();
    }

    private static Boolean isConstantType(Type symbolType) {
        return symbolType.isPrimitive() || symbolType.is("java.lang.String") || ((JavaType) symbolType).isPrimitiveWrapper();
        //return symbolType.isPrimitive() || symbolType.is("java.lang.String");
    }

    private static Boolean isNotOverriden(MethodTree methodTree) {
        return Boolean.FALSE.equals(methodTree.isOverriding());
    }
}
