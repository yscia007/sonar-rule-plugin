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
package com.jd.sonar.java.itqa.plugin.checks.other;


import org.sonar.check.Rule;
import org.sonar.java.checks.helpers.ExpressionsHelper;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ImportTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

/**
 * @author: yangshuo8
 *  @date: 2019/3/16
 * @desc:
 * */
@Rule(key = "p3c011")
public class AvoidApacheBeanUtilsCopyRule extends BaseTreeVisitor implements JavaFileScanner {

    private static final String BEANUTILS = "BeanUtils";
    private static final String APACHE_BEAN_UTILS = "org.apache.commons.beanutils.BeanUtils";
    private static final String COPY_PROPERTIES = "copyProperties";
    private static final String BEANUTILS_COPY = "BeanUtils.copyProperties";
    private JavaFileScannerContext context;
    private Boolean apacheBeanutilsFlag;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        apacheBeanutilsFlag = false;
        scan(context.getTree());
    }

    @Override
    public void visitImport(ImportTree tree) {
        if (tree.qualifiedIdentifier().is(Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree mset = (MemberSelectExpressionTree) tree.qualifiedIdentifier();
            if (BEANUTILS.equals(mset.identifier().name()) && APACHE_BEAN_UTILS.equals(ExpressionsHelper.concatenate(mset))) {
                apacheBeanutilsFlag = true;
            }
        }
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree mit) {
        if (!apacheBeanutilsFlag) {
            return;
        }
        if (mit.methodSelect().is(Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree mset = (MemberSelectExpressionTree) mit.methodSelect();
            if (COPY_PROPERTIES.equals(mset.identifier().name()) && BEANUTILS_COPY.equals(ExpressionsHelper.concatenate(mset))) {
                context.reportIssue(this, mset.identifier(), "避免使用Apache Beanutils类的方法去拷贝属性");
            }
        }
    }
}
