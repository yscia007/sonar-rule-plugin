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
 * @date: 2019-04-15 15:59
 * @desc: description
 */
@Rule(key = "QA126")
public class ArbitrarySqlCommandExecutionCheck extends AbstractMybatisMapperXpathBasedCheck {

    private XPathExpression sqlStatementExpression = getXPathExpression("//select|//update|//insert|//delete");
    private static final Pattern PATTERN_SQL = Pattern.compile("^\\$\\{\\S+\\}$");

    @Override
    protected void scanMybatisMapperFile(XmlFile file) {
        List<Node> sqlNodes = evaluateAsList(sqlStatementExpression, file.getNamespaceUnawareDocument());
        if (sqlNodes.isEmpty()) {
            return;
        }
        for (Node node : sqlNodes) {
            if (hasAnySqlCommandExecution(node)) {
                reportIssue(node, "这种写法会导致任意sql命令执行漏洞");
            }
        }
    }

    private static Boolean hasAnySqlCommandExecution(Node node) {
        // 去除字符串中的换行符、回车及空格；
        String sql = node.getTextContent().replaceAll("\r|\n|\\s", "");
        return PATTERN_SQL.matcher(sql).matches();
    }
}
