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
package com.jd.sonar.java.itqa.plugin.checks.xml.mybatis;

import org.sonarsource.analyzer.commons.xml.XmlFile;
import org.sonarsource.analyzer.commons.xml.checks.SimpleXPathBasedCheck;

/**
 * @author: yangshuo8
 * @date: 2019-04-03 18:04
 * @desc: description
 */
public abstract class AbstractMybatisMapperXpathBasedCheck extends SimpleXPathBasedCheck {

    private static final String MAPPER_SUFFIX = "Mapper.xml";

    @Override
    public void scanFile(XmlFile file) {
        if (isMybatisMapperFile(file)) {
            scanMybatisMapperFile(file);
        }
    }

    /**
     * scan mybatis mapper xml file.
     *
     * @param file
     */
    protected abstract void scanMybatisMapperFile(XmlFile file);

    private static Boolean isMybatisMapperFile(XmlFile file) {
        return file.getInputFile().filename().endsWith(MAPPER_SUFFIX);
    }
}
