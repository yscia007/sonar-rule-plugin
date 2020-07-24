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

/**
 * @author: yangshuo8
 * @date: 2020-06-16 14:14
 * @desc: description
 */
@Rule(key = "QA129")
public class SqlStatementProbeCheck extends AbstractMybatisMapperXpathBasedCheck {

    private XPathExpression sqlStatementExpression = getXPathExpression("//select|//update|//insert|//delete");

    @Override
    protected void scanMybatisMapperFile(XmlFile file) {
        List<Node> sqlNodes = evaluateAsList(sqlStatementExpression, file.getNamespaceUnawareDocument());
        if (sqlNodes.isEmpty()) {
            return;
        }
        int sqlStatementCount = sqlNodes.size();
        reportIssue(sqlNodes.get(0), String.valueOf(sqlStatementCount));
    }
}
