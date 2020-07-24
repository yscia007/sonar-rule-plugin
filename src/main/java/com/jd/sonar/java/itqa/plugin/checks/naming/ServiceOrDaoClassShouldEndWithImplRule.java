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
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

/**
 * @author: zhangliwei29
 * @date: 2019/2/26
 * @desc: [Mandatory] All Service and DAO classes must be interface based on SOA principle.
 * Implementation class names should be ended with Impl.
 * */


@Rule(key = "p3c034")
public class ServiceOrDaoClassShouldEndWithImplRule extends IssuableSubscriptionVisitor {

    private static final String SERVICE_SUFFIX = "Service";
    private static final String DAO_SUFFIX = "DAO";
    private static final String SUFFIX = "Impl";

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;
        if (hasServiceOrDaoSuperInterface(classTree) && !isAbstract(classTree) && !hasCorrectNaming(classTree)) {
            reportIssue(classTree.simpleName(), "对于 Service 和 DAO 类，基于 SOA 的理念，暴露出来的服务一定是接口，内部的实现类用Impl的后缀与接口区别。");
        }
    }

    private static Boolean isAbstract(ClassTree tree) {
        return tree.symbol().isAbstract();
    }

    private static Boolean hasServiceOrDaoSuperInterface(ClassTree tree) {
        if (tree.superInterfaces().isEmpty()) {
            return false;
        }
        List<TypeTree> typeTrees = tree.superInterfaces();
        for (TypeTree typeTree : typeTrees) {
            if (hasServiceOrDaoSuperInterface(typeTree)) {
                return true;
            }
        }
        return false;
    }

    private static Boolean hasServiceOrDaoSuperInterface(TypeTree typeTree) {
        if (typeTree.is(Kind.IDENTIFIER)) {
            String superInterfaceName = ((IdentifierTree) typeTree).name();
            return superInterfaceName.endsWith(SERVICE_SUFFIX) || superInterfaceName.endsWith(DAO_SUFFIX);
        } else if (typeTree.is(Kind.PARAMETERIZED_TYPE)) {
            ParameterizedTypeTree ptt = (ParameterizedTypeTree) typeTree;
            if (ptt.type().is(Kind.IDENTIFIER)) {
                String superInterfaceName = ((IdentifierTree) ptt.type()).name();
                return superInterfaceName.endsWith(SERVICE_SUFFIX) || superInterfaceName.endsWith(DAO_SUFFIX);
            }
        }
        return false;
    }

    private static Boolean hasCorrectNaming(ClassTree tree) {
        if (tree.simpleName() == null) {
            return true;
        }
        return tree.simpleName().name().endsWith(SUFFIX);
    }
}
