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
 * @date: 2019-04-03 18:18
 * @desc: description
 */
@Rule(key = "QA122")
public class DollarSignInSqlStatementCheck extends AbstractMybatisMapperXpathBasedCheck {

    private XPathExpression sqlStatementExpression = getXPathExpression("//select|//update|//insert|//delete");
    private static final Pattern PATTERN_DOLLAR = Pattern.compile(".*\\$\\{\\S+\\}.*", Pattern.DOTALL);
    private static final String WHERE = "where";
    private static final String LIMIT = "limit";
    private static final String ORDER_BY = "order by";
    private static final String GROUP_BY = "group by";

    @Override
    protected void scanMybatisMapperFile(XmlFile file) {
        List<Node> sqlNodes = evaluateAsList(sqlStatementExpression, file.getNamespaceUnawareDocument());
        if (sqlNodes.isEmpty()) {
            return;
        }
        for (Node node : sqlNodes) {
            if (hasStringConcatenationInSqlQuery(node)) {
                reportIssue(node, "请将sql语句参数操作中的'${}'修改为'#{}'");
            }
        }
    }

    private static Boolean hasStringConcatenationInSqlQuery(Node node) {
        String sql = node.getTextContent();
        boolean shouldCheck = PATTERN_DOLLAR.matcher(sql).matches();
        if (!shouldCheck) {
            return false;
        }
        String lowerCaseSql = sql.toLowerCase();
        boolean hasWhereCondition = lowerCaseSql.contains(WHERE);
        boolean hasLimitCondition = lowerCaseSql.contains(LIMIT);
        boolean hasOrderByCondition = lowerCaseSql.contains(ORDER_BY);
        boolean hasGroupByCondition = lowerCaseSql.contains(GROUP_BY);
        // 验证sql语句Where条件中是否使用了'$'，order by,group by中使用的${}将会被忽略
        if (hasWhereCondition) {
            int beginIndex = lowerCaseSql.lastIndexOf(WHERE);
            if (hasOrderByCondition) {
                int endIndex = lowerCaseSql.indexOf(ORDER_BY);
                return beginIndex < endIndex && PATTERN_DOLLAR.matcher(lowerCaseSql.substring(beginIndex, endIndex)).matches();
            }
            if (hasGroupByCondition) {
                int endIndex = lowerCaseSql.indexOf(GROUP_BY);
                return beginIndex < endIndex && PATTERN_DOLLAR.matcher(lowerCaseSql.substring(beginIndex, endIndex)).matches();
            }
            if (hasLimitCondition) {
                int endIndex = lowerCaseSql.indexOf(LIMIT);
                return beginIndex < endIndex && PATTERN_DOLLAR.matcher(lowerCaseSql.substring(beginIndex, endIndex)).matches();
            }
            return PATTERN_DOLLAR.matcher(lowerCaseSql.substring(beginIndex)).matches();
        }
        return false;
    }
}
