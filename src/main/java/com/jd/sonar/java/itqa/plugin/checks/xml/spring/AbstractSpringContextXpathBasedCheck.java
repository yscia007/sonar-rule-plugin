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
package com.jd.sonar.java.itqa.plugin.checks.xml.spring;

import com.google.common.collect.ImmutableSet;
import org.sonarsource.analyzer.commons.xml.XmlFile;
import org.sonarsource.analyzer.commons.xml.checks.SimpleXPathBasedCheck;

import java.util.Set;

/**
 * @author: yangshuo8
 * @date: 2019-03-28 18:15
 * @desc: description
 */
public abstract class AbstractSpringContextXpathBasedCheck extends SimpleXPathBasedCheck {

    private static final Set<String> SPRING_CONTEXT_FILE = ImmutableSet.of(
            "applicationContext.xml",
            "spring-config.xml",
            "application-context.xml",
            "app-config.xml",
            "spring.xml",
            "spring-worker.xml"
    );

    @Override
    public void scanFile(XmlFile file) {
        if (isSpringContextFile(file)) {
            scanSpringContextFile(file);
        }
    }

    /**
     * scan spring application context xml file.
     *
     * @param file xml file
     */
    public abstract void scanSpringContextFile(XmlFile file);

    private static Boolean isSpringContextFile(XmlFile file) {
        return SPRING_CONTEXT_FILE.contains(file.getInputFile().filename());
    }
}
