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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.model.ModifiersUtils;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangshuo27
 * @date: 2019/1/15
 * @time: 下午3:42
 * @desc: Avoid Define Local Variable For XStream Rule Implementation.
 */
@Rule(
        key = "QA101",
        name = "AvoidDefineLocalVariableForXstreamRule",
        description = "XStream类是线程安全的，不需要重复初始化XStream对象，如果使用不当会影响系统性能，例如：每次处理请求时都实例化一个新的XStream对象，没有把相同类型的缓存起来使用，会导致性能问题。",
        priority = Priority.MAJOR,
        tags = {"performance", "bug", "itqa"})
public class AvoidDefineLocalVariableForXstreamRule extends BaseTreeVisitor implements JavaFileScanner {

    private static final Logger LOGGER  = LoggerFactory.getLogger(AvoidDefineLocalVariableForXstreamRule.class);
    private JavaFileScannerContext context;
    private static final String VARIABLE_TYPE = "XStream";
    private boolean staticMethodFlag = false;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        for (Tree member : tree.members()) {
            if (member.is(Tree.Kind.METHOD)) {
                scan(member);
            }
        }
    }

    @Override
    public void visitMethod(MethodTree tree) {
        if (isStaticMethod(tree)) {
            staticMethodFlag = true;
        } else {
            staticMethodFlag = false;
        }
        super.visitMethod(tree);
    }

    @Override
    public void visitVariable(VariableTree tree) {
        if (! staticMethodFlag && isLocalVariableOfXstream(tree)) {
            LOGGER.debug("found a issue about XStream.");
            IdentifierTree simpleName = tree.simpleName();
            String message = String.format("应将XStream变量 \"%s\" 定义为全局变量,在方法体内定义为局部变量后频繁初始化对性能影响较大。", simpleName.name());
            context.reportIssue(this, simpleName, message);
        }
        super.visitVariable(tree);
    }

    /**
    * @Description 判断XStream类型变量是否为局部变量
    * @Param
    * @return
    */
    private boolean isLocalVariableOfXstream(VariableTree tree) {
        return context.getSemanticModel() != null && VARIABLE_TYPE.equals(tree.type().toString());
    }

    /**
    * @Description 判断当前方法是否为静态方法
    * @Param
    * @return
    */
    private static boolean isStaticMethod(MethodTree tree) {
        return ModifiersUtils.hasModifier(tree.modifiers(), Modifier.STATIC);
    }
}
