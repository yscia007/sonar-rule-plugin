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
package com.jd.sonar.java.itqa.plugin.checks.xml.maven;

import com.jd.sonar.java.itqa.plugin.checks.helpers.ItqaMavenDependencyMatcher;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonarsource.analyzer.commons.xml.XmlFile;
import org.sonarsource.analyzer.commons.xml.checks.SimpleXPathBasedCheck;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpression;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yangshuo8
 * @date: 2019-04-01 10:57
 * @desc: Rule template for insecure dependency check.
 */
@Rule(key = InsecureDependenciesCheck.KEY)
public class InsecureDependenciesCheck extends SimpleXPathBasedCheck {

    public static final String KEY = "QA113";

    private static final String POM = "pom.xml";
    private static final String GROUP_ID = "groupId";
    private static final String ARTIFACT_ID = "artifactId";
    private static final String VERSION_NAME = "version";
    private XPathExpression dependencyExpression = getXPathExpression("//dependencies/dependency");
    private XPathExpression propertyExpression = getXPathExpression("//properties");
    private ItqaMavenDependencyMatcher matcher = null;

    @RuleProperty(
            key = "dependencyName",
            description = "Pattern describing insecure dependencies group and artifact ids. E.G. '``*:.*log4j``' or '``x.y:*``'")
    public String dependencyName = "";

    @RuleProperty(
            key = "version",
            description = "Insecure dependency version pattern or dash-delimited range. Leave blank for all versions. E.G. '``1.3.*``', '``1.0-3.1``', '``1.0-*``', '``*-1.2.5|2.3.0-2.3.6``' or '``*-3.1``'")
    public String version = "";

    @RuleProperty(
            key = "message",
            description = "Issue message. E.G. 'The current version of fastjson has known vulnerability, please upgrade to secure version.'")
    public String message = "";
    
    @Override
    public void scanFile(XmlFile file) {
        if (!isMavenPomFile(file)) {
            return;
        }
        // 获取所有的dependency列表
        List<Node> nodes = evaluateAsList(dependencyExpression, file.getNamespaceUnawareDocument());
        // 获取所有的properties列表，pom文件中只能包含一个properties
        List<Node> properties = evaluateAsList(propertyExpression, file.getNamespaceUnawareDocument());
        // 获取<properties>中的所有版本变量值映射
        Map<String, String> propertyMap = getAllProperty(properties);
        for (Node node : nodes) {
            String groupId = getChildElementText(GROUP_ID, node);
            String artifactId = getChildElementText(ARTIFACT_ID, node);
            String versionValue = getChildElementText(VERSION_NAME, node);
            if (versionValue.startsWith("${")) {
                String versionVariableName = versionValue.substring(2, versionValue.length() - 1);
                if (propertyMap.containsKey(versionVariableName)) {
                    versionValue = propertyMap.get(versionVariableName);
                }
            }
            if (getMatcher().matches(groupId, artifactId, versionValue)) {
                reportIssue(node, message);
            }
        }
    }

    private static Boolean isMavenPomFile(XmlFile file) {
        return POM.equalsIgnoreCase(file.getInputFile().filename());
    }

    private static String getChildElementText(String childElementText, Node parent) {
        for (Node node : XmlFile.children(parent)) {
            boolean isCurrentNode = node.getNodeType() == Node.ELEMENT_NODE &&
                    ((Element) node).getTagName().equals(childElementText);
            if (isCurrentNode) {
                return node.getTextContent();
            }
        }
        return "";
    }

    private static Map<String, String> getAllProperty(List<Node> properties) {
        if (properties.isEmpty()) {
            return new HashMap<>(0);
        }
        Map<String, String> propertyMap = new HashMap<>(16);
        for (Node node : properties) {
            for (Node property : XmlFile.children(node)) {
                if (property.getNodeType() == Node.ELEMENT_NODE) {
                    String key = ((Element) property).getTagName();
                    propertyMap.put(key, property.getTextContent());
                }
            }
        }
        return propertyMap;
    }

    private ItqaMavenDependencyMatcher getMatcher() {
        if (matcher == null) {
            try {
                matcher = new ItqaMavenDependencyMatcher(dependencyName, version);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("[" + KEY + "] Unable to build matchers from provided dependency name: " + dependencyName, e);
            }
        }
        return matcher;
    }
}
