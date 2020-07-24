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
 * @date: 2019-10-28 11:24
 * @desc: 扫描spring项目jsf配置文件，获取jsf生产者接口名称
 */
@Rule(key = "QA128")
public class SpringJsfProviderCheck extends AbstractSpringJsfConfigXpathBasedCheck {

    private XPathExpression jsfProviderExpression = getXPathExpression("//*[local-name()='provider'][@interface]");

    @Override
    public void scanSpringJsfConfigFile(XmlFile file) {
        List<Node> nodes = evaluateAsList(jsfProviderExpression, file.getNamespaceUnawareDocument());
        if (nodes.isEmpty()) {
            return;
        }
        for (Node node : nodes) {
            String jsfInterface = node.getAttributes().getNamedItem("interface").getNodeValue();
            reportIssue(node, jsfInterface);
        }
    }
}
