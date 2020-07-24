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

import org.sonar.check.Rule;
import org.sonarsource.analyzer.commons.xml.XmlFile;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpression;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: yangshuo8
 * @date: 2019-04-04 20:28
 * @desc: description
 */
@Rule(key = "QA123")
public class WildcardInSqlQueryStatementCheck extends AbstractMybatisMapperXpathBasedCheck {

    private XPathExpression sqlQueryExpression = getXPathExpression("//select");
    private static final Pattern PATTERN_WILDCARD = Pattern.compile(".*(select)\\s+\\*\\s+(from).*", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);

    @Override
    protected void scanMybatisMapperFile(XmlFile file) {
        List<Node> nodes = evaluateAsList(sqlQueryExpression, file.getNamespaceUnawareDocument());
        if (nodes.isEmpty()) {
            return;
        }
        for (Node node : nodes) {
            if (hasWildcardInSqlQueryStatement(node)) {
                reportIssue(node, "SELECT查询语句中请明确写明需要哪些字段，而不是使用 * ");
            }
        }
    }

    private static Boolean hasWildcardInSqlQueryStatement(Node node) {
        String sql = node.getTextContent();
        return PATTERN_WILDCARD.matcher(sql).matches();
    }
}
