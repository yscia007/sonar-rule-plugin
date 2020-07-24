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

import org.sonar.check.Rule;
import org.sonarsource.analyzer.commons.xml.XmlFile;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpression;
import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019-03-27 15:47
 * @desc: Spring config param default-lazy-init="true" check.
 */
@Rule(key = "QA112")
public class SpringDefaultLazyInitCheck extends AbstractSpringContextXpathBasedCheck {

    private XPathExpression defaultLazyInitExpression = getXPathExpression("beans[@default-lazy-init='true']");

    @Override
    public void scanSpringContextFile(XmlFile file) {
        List<Node> nodes = evaluateAsList(defaultLazyInitExpression, file.getNamespaceUnawareDocument());
        if (nodes.isEmpty()) {
            return;
        }
        reportIssue(nodes.get(0), "关闭Spring懒加载吧: <beans default-lazy-init='false'>");
    }
}
