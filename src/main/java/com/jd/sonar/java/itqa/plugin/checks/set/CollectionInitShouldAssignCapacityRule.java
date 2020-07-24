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
package com.jd.sonar.java.itqa.plugin.checks.set;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.java.matcher.MethodMatcherCollection;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019-02-14 11:16
 * @desc: description
 */
@Rule(key = "p3c009")
public class CollectionInitShouldAssignCapacityRule extends IssuableSubscriptionVisitor {

    private static final MethodMatcherCollection COLLECTION_CONSTRUCTOR_METHODS = MethodMatcherCollection.create(
            MethodMatcher.create().typeDefinition("java.util.ArrayDeque").name("<init>").withoutParameter(),
            MethodMatcher.create().typeDefinition("java.util.concurrent.ConcurrentLinkedDeque").name("<init>").withoutParameter(),

            MethodMatcher.create().typeDefinition("java.util.ArrayList").name("<init>").withoutParameter(),
            MethodMatcher.create().typeDefinition("java.util.CopyOnWriteArrayList").name("<init>").withoutParameter(),
            MethodMatcher.create().typeDefinition("java.util.LinkedList").name("<init>").withoutParameter(),

            MethodMatcher.create().typeDefinition("java.util.EnumMap").name("<init>").withoutParameter(),
            MethodMatcher.create().typeDefinition("java.util.HashMap").name("<init>").withoutParameter(),
            MethodMatcher.create().typeDefinition("java.util.Hashtable").name("<init>").withoutParameter(),
            MethodMatcher.create().typeDefinition("java.util.IdentityHashMap").name("<init>").withoutParameter(),
            MethodMatcher.create().typeDefinition("java.util.LinkedHashMap").name("<init>").withoutParameter(),
            MethodMatcher.create().typeDefinition("java.util.WeakHashMap").name("<init>").withoutParameter(),

            MethodMatcher.create().typeDefinition("java.util.concurrent.ConcurrentHashMap").name("<init>").withoutParameter(),
            MethodMatcher.create().typeDefinition("java.util.concurrent.ConcurrentSkipListMap").name("<init>").withoutParameter(),

            MethodMatcher.create().typeDefinition("java.util.concurrent.ConcurrentLinkedQueue").name("<init>").withoutParameter(),
            MethodMatcher.create().typeDefinition("java.util.concurrent.SynchronousQueue").name("<init>").withoutParameter(),

            MethodMatcher.create().typeDefinition("java.util.concurrent.CopyOnWriteArraySet").name("<init>").withoutParameter(),
            MethodMatcher.create().typeDefinition("java.util.EnumSet").name("<init>").withoutParameter(),
            MethodMatcher.create().typeDefinition("java.util.HashSet").name("<init>").withoutParameter(),
            MethodMatcher.create().typeDefinition("java.util.LinkedHashSet").name("<init>").withoutParameter(),

            MethodMatcher.create().typeDefinition("java.util.TreeMap").name("<init>").withoutParameter(),
            MethodMatcher.create().typeDefinition("java.util.TreeSet").name("<init>").withoutParameter()
    );

    private Boolean isInMethod = false;

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.NEW_CLASS, Kind.METHOD);
    }

    @Override
    public void visitNode(Tree tree) {
        if (tree.is(Kind.METHOD)) {
            isInMethod = true;
        }
        if (tree.is(Kind.NEW_CLASS)) {
            NewClassTree newClassTree = (NewClassTree) tree;
            if (COLLECTION_CONSTRUCTOR_METHODS.anyMatch(newClassTree) && isInMethod) {
                reportIssue(tree, "集合初始化时应该指定容量大小，如果暂时无法确定初始值大小，请设置为 16");
            }
        }
    }

    @Override
    public void leaveNode(Tree tree) {
        if (tree.is(Kind.METHOD)) {
            isInMethod = false;
        }
    }
}
