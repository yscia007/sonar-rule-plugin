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
package com.jd.sonar.java.itqa.plugin.checks.helpers;

import org.sonar.plugins.java.api.semantic.SymbolMetadata;
import org.sonar.plugins.java.api.tree.MethodTree;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public final class UnitTestUtils {

    private static final Set<String> TEST_ANNOTATIONS = new HashSet<>(asList("org.junit.Test", "org.testng.annotations.Test"));
    private static final Set<String> JUNIT5_TEST_ANNOTATIONS = new HashSet<>(asList(
            "org.junit.jupiter.api.Test",
            "org.junit.jupiter.api.RepeatedTest",
            "org.junit.jupiter.api.TestFactory",
            "org.junit.jupiter.api.TestTemplate",
            "org.junit.jupiter.params.ParameterizedTest"));

    private UnitTestUtils() {
    }

    public static boolean hasTestAnnotation(MethodTree tree) {
        SymbolMetadata symbolMetadata = tree.symbol().metadata();
        return TEST_ANNOTATIONS.stream().anyMatch(symbolMetadata::isAnnotatedWith) || hasJUnit5TestAnnotation(symbolMetadata);
    }

    public static boolean hasJUnit5TestAnnotation(MethodTree tree) {
        return hasJUnit5TestAnnotation(tree.symbol().metadata());
    }

    private static boolean hasJUnit5TestAnnotation(SymbolMetadata symbolMetadata) {
        return JUNIT5_TEST_ANNOTATIONS.stream().anyMatch(symbolMetadata::isAnnotatedWith);
    }

}
