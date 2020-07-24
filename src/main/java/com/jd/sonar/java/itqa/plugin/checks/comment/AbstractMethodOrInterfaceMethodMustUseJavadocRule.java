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
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: zhangliwei29
 *  @date: 2019/2/26
 * */

@Rule(key = "p3c044")
public class AbstractMethodOrInterfaceMethodMustUseJavadocRule extends IssuableSubscriptionVisitor {

    private static final Pattern PARAM = Pattern.compile(".*@param.*",
            Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

    private static final Pattern RETURN = Pattern.compile(".*@return.*",
            Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

    private static final Pattern THROWS = Pattern.compile(".*@throws.*",
            Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

    private static final String VOID = "void";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.of(Tree.Kind.METHOD);
    }

    @Override
    public void visitNode(Tree tree) {
//        if (tree.is(Tree.Kind.METHOD)) {
//            MethodTree methodTree = (MethodTree) tree;
//            if (methodTree.symbol().isAbstract()) {
//                ModifiersTree modifierTrees = (ModifiersTree) methodTree.modifiers();
//                if (!modifierTrees.isEmpty()) {
//                    ModifierKeywordTree modifierKeywordTree = (ModifierKeywordTree) modifierTrees.get(0);
//                    if (modifierKeywordTree.firstToken().trivias().isEmpty()) {
//                        reportIssue(tree, "缺少注释信息,删除空注释");
//                    } else {
//                        for (int r = 0; r < modifierKeywordTree.firstToken().trivias().size(); r++) {
//                            if (modifierKeywordTree.firstToken().trivias().get(r).comment().contains("/**")) {
//                                String aa = modifierKeywordTree.firstToken().trivias().get(r).comment();
//                                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//                                Matcher m = p.matcher(aa);
//                                String aAfter = m.replaceAll("");
//                                Boolean hasParam = true;
//                                Boolean hasReturn = true;
//                                Boolean hasThrow = true;
//                                if (methodTree.returnType().is(Tree.Kind.PRIMITIVE_TYPE)) {
//                                    PrimitiveTypeTree primitiveTypeTree = (PrimitiveTypeTree) methodTree.returnType();
//                                    String type = primitiveTypeTree.symbolType().name();
//                                    if (!"void".equals(type)) {
//                                        hasParam = PARAM.matcher(aa).matches();
//                                    }
//                                } else {
//                                    if (!methodTree.returnType().equals("void")) {
//                                        hasParam = PARAM.matcher(aa).matches();
//                                    }
//                                }
//                                if (!methodTree.parameters().isEmpty()) {
//                                    int paCount = 0;
//                                    String[] pa = aAfter.split("\\*");
//                                    for (int a = 0; a < pa.length; a++) {
//                                        if (pa[a].contains("@param")) {
//                                            paCount++;
//                                        }
//                                    }
//                                    if (PARAM.matcher(aa).matches() && paCount == methodTree.parameters().size()) {
//                                        hasReturn = true;
//                                    } else {
//                                        hasReturn = false;
//                                    }
//                                }
//                                if (!methodTree.throwsClauses().isEmpty()) {
//                                    int taCount = 0;
//                                    String[] pa = aAfter.split("\\*");
//                                    for (int a = 0; a < pa.length; a++) {
//                                        if (pa[a].contains("@throws")) {
//                                            taCount++;
//                                        }
//                                    }
//                                    if (PARAM.matcher(aa).matches() && taCount == methodTree.parameters().size()) {
//                                        hasThrow = true;
//                                    } else {
//                                        hasThrow = false;
//                                    }
//                                }
//                                if (!hasParam || !hasReturn || !hasThrow) {
//                                    reportIssue(methodTree, "注释信息不全");
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    if (methodTree.returnType().is(Tree.Kind.IDENTIFIER)) {
//                        IdentifierTree identifierTree = (IdentifierTree) methodTree.returnType();
//                        if (!identifierTree.firstToken().trivias().isEmpty()) {
//                            for (int w = 0; w < identifierTree.firstToken().trivias().size(); w++) {
//                                if (identifierTree.firstToken().trivias().get(w).comment().contains("/**")) {
//                                    String cName = identifierTree.firstToken().trivias().get(0).comment();
//                                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//                                    Matcher m = p.matcher(cName);
//                                    String aAfter = m.replaceAll("");
//
//                                    Boolean hasParam = true;
//                                    Boolean hasReturn = true;
//                                    Boolean hasThrow = true;
//                                    if (methodTree.returnType().is(Tree.Kind.PRIMITIVE_TYPE)) {
//                                        PrimitiveTypeTree primitiveTypeTree = (PrimitiveTypeTree) methodTree.returnType();
//                                        String type = primitiveTypeTree.symbolType().name();
//                                        if (!VOID.equals(type)) {
//                                            hasParam = PARAM.matcher(cName).matches();
//                                        }
//
//                                    } else {
//                                        if (!methodTree.returnType().equals("void")) {
//                                            hasParam = PARAM.matcher(cName).matches();
//                                        }
//                                    }
//
//                                    if (!methodTree.parameters().isEmpty()) {
//                                        int pCount = 0;
//                                        String[] pa = aAfter.split("\\*");
//                                        for (int a = 0; a < pa.length; a++) {
//                                            if (pa[a].contains("@param")) {
//                                                pCount++;
//                                            }
//                                        }
//                                        if (PARAM.matcher(cName).matches() && pCount == methodTree.parameters().size()) {
//                                            hasReturn = true;
//                                        } else {
//                                            hasReturn = false;
//                                        }
//                                    }
//                                    if (!methodTree.throwsClauses().isEmpty()) {
//                                        int tCount = 0;
//                                        String[] ppa = aAfter.split("\\*");
//                                        for (int i = 0; i < ppa.length; i++) {
//                                            if (ppa[i].contains("@throws")) {
//                                                tCount++;
//                                            }
//                                        }
//                                     if (PARAM.matcher(cName).matches() && tCount == methodTree.parameters().size()) {
//                                            hasThrow = true;
//                                        } else {
//                                            hasThrow = false;
//                                        }
//                                    }
//                                    if (!hasParam || !hasReturn || !hasThrow) {
//                                        reportIssue(methodTree, "缺少注释信息");
//                                    }
//                                }
//                            }
//                        } else {
//                            Boolean hasParam = false;
//                            Boolean hasReturn = false;
//                            Boolean hasThrow = false;
//                            if (methodTree.returnType().is(Tree.Kind.PRIMITIVE_TYPE)) {
//                                PrimitiveTypeTree primitiveTypeTree = (PrimitiveTypeTree) methodTree.returnType();
//                                String type = primitiveTypeTree.symbolType().name();
//                                if ("void".equals(type)) {
//                                    hasParam = true;
//                                }
//                            } else {
//                                if (methodTree.returnType().equals("void")) {
//                                    hasParam = true;
//                                }
//                            }
//                            if (methodTree.parameters().isEmpty()) {
//                                hasReturn = true;
//                            }
//                            if (methodTree.throwsClauses().isEmpty()) {
//                                hasThrow = true;
//                            }
//                            if (!hasParam || !hasReturn || !hasThrow) {
//                                reportIssue(methodTree, "缺少注释信息");
//                            }
//                        }
//                    } else if (!methodTree.returnType().is(Tree.Kind.IDENTIFIER)) {
//                        if (!methodTree.returnType().firstToken().trivias().isEmpty()) {
//                            for (int w = 0; w < methodTree.returnType().firstToken().trivias().size(); w++) {
//                                if (methodTree.returnType().firstToken().trivias().get(w).comment().contains("/**")) {
//                                    String commentName = methodTree.returnType().firstToken().trivias().get(w).comment();
//                                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//                                    Matcher m = p.matcher(commentName);
//                                    String aAfter = m.replaceAll("");
//
//                                    Boolean hasParam = true;
//                                    Boolean hasReturn = true;
//                                    Boolean hasThrow = true;
//                                    if (methodTree.returnType().is(Tree.Kind.PRIMITIVE_TYPE)) {
//                                        PrimitiveTypeTree primitiveTypeTree = (PrimitiveTypeTree) methodTree.returnType();
//                                        String type = primitiveTypeTree.symbolType().name();
//                                        if (!"void".equals(type)) {
//                                            hasParam = PARAM.matcher(commentName).matches();
//                                        }
//                                    } else {
//                                        if (!VOID.equals(methodTree.returnType())) {
//                                            hasParam = PARAM.matcher(commentName).matches();
//                                        }
//                                    }
//                                    if (!methodTree.parameters().isEmpty()) {
//                                        int paCount = 0;
//                                        String[] pa = aAfter.split("\\*");
//                                        for (int a = 0; a < pa.length; a++) {
//                                            if (pa[a].contains("@param")) {
//                                                paCount++;
//                                            }
//                                        }
//                                        if (paCount == methodTree.parameters().size()) {
//                                            hasReturn = PARAM.matcher(commentName).matches();
//                                        }
//                                    }
//                                    if (!methodTree.throwsClauses().isEmpty()) {
//                                        int taCount = 0;
//                                        String[] pa = aAfter.split("\\*");
//                                        for (int a = 0; a < pa.length; a++) {
//                                            if (pa[a].contains("@throws")) {
//                                                taCount++;
//                                            }
//                                        }
//                                        if (taCount == methodTree.parameters().size()) {
//                                            hasThrow = PARAM.matcher(commentName).matches();
//                                        }
//                                    }
//                                    if (!hasParam || !hasReturn || !hasThrow) {
//                                        reportIssue(methodTree, "缺少注释信息");
//                                    }
//                                    if (hasParam && methodTree.parameters().isEmpty() && methodTree.throwsClauses().isEmpty()) {
//                                        if (aAfter.equals("/***/")) {
//                                            reportIssue(methodTree, "删除无用的空注释");
//                                        }
//                                    }
//                                } else {
//                                    if (methodTree.returnType().firstToken().trivias().size() > 1) {
//                                        break;
//                                    }
//                                    Boolean hasParam = false;
//                                    Boolean hasReturn = false;
//                                    Boolean hasThrow = false;
//                                    if (methodTree.returnType().is(Tree.Kind.PRIMITIVE_TYPE)) {
//                                        PrimitiveTypeTree primitiveTypeTree = (PrimitiveTypeTree) methodTree.returnType();
//                                        String type = primitiveTypeTree.symbolType().name();
//                                        if (VOID.equals(type)) {
//                                            hasParam = true;
//                                        }
//                                    } else {
//                                        if (VOID.equals(methodTree.returnType())) {
//                                            hasParam = true;
//                                        }
//                                    }
//                                    if (methodTree.parameters().isEmpty()) {
//                                        hasReturn = true;
//                                    }
//                                    if (methodTree.throwsClauses().isEmpty()) {
//                                        hasThrow = true;
//                                    }
//                                    if (!hasParam || !hasReturn || !hasThrow) {
//                                        reportIssue(methodTree, "没有注释信息");
//                                    }
//                                }
//                            }
//                        } else {
//                            Boolean hasParam = false;
//                            Boolean hasReturn = false;
//                            Boolean hasThrow = false;
//                            if (methodTree.returnType().is(Tree.Kind.PRIMITIVE_TYPE)) {
//                                PrimitiveTypeTree primitiveTypeTree = (PrimitiveTypeTree) methodTree.returnType();
//                                String type = primitiveTypeTree.symbolType().name();
//                                if ("void".equals(type)) {
//                                    hasParam = true;
//                                }
//                            } else {
//                                if (("void").equals(methodTree.returnType())) {
//                                    hasParam = true;
//                                }
//                            }
//                            if (methodTree.parameters().isEmpty()) {
//                                hasReturn = true;
//                            }
//                            if (methodTree.throwsClauses().isEmpty()) {
//                                hasThrow = true;
//                            }
//                            if (!hasParam || !hasReturn || !hasThrow) {
//                                reportIssue(methodTree, "没有注释信息");
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }
}
