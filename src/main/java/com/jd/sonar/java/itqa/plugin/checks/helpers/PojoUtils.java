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

import org.sonar.plugins.java.api.tree.ClassTree;

/**
 * @author: yangshuo8
 * @date: 2019-02-27 18:13
 * @desc: description
 */
public class PojoUtils {

    private static final String POJO_REGEX = "(.*)(DO|DTO|BO|VO)\\b";

    private PojoUtils() {}

    public static Boolean isPojo(String className) {
       return className != null && className.matches(POJO_REGEX);
    }

    public static Boolean isPojo(ClassTree tree) {
        return tree != null && tree.simpleName() != null && isPojo(tree.simpleName().name());
    }
}
