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

import org.sonarsource.analyzer.commons.xml.XmlFile;
import org.sonarsource.analyzer.commons.xml.checks.SimpleXPathBasedCheck;

/**
 * @author: yangshuo8
 * @date: 2019-10-28 10:53
 * @desc: description
 */
public abstract class AbstractSpringJsfConfigXpathBasedCheck extends SimpleXPathBasedCheck {

    private static final String IGNORE_FILE_REGEX = "(pom.xml|(.*)Mapper.xml)";

//    private static final Set<String> SPRING_JSF_CONFIG_FILE = ImmutableSet.of(
//            "spring-jsf-provider.xml",
//            "Application-jsf.xml",
//            "spring-config-jsf.xml",
//            "SpringJsfConfig.xml"
//    );

    @Override
    public void scanFile(XmlFile file) {
        boolean shouldSkip = file.getInputFile().filename().matches(IGNORE_FILE_REGEX);
        if (!shouldSkip) {
            scanSpringJsfConfigFile(file);
        }
    }

    /**
     * scan spring jsf config xml file.
     *
     * @param file xml file
     */
    public abstract void scanSpringJsfConfigFile(XmlFile file);
}
