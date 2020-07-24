package com.jd.sonar.java.itqa.plugin.checks.xml.mybatis;

import org.junit.Test;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheck;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

/**
 * @author: yangshuo8
 * @date: 2019-04-04 20:29
 * @desc: description
 */
public class WildcardInSqlQueryStatementCheckTest {

    private SonarXmlCheck check = new WildcardInSqlQueryStatementCheck();

    @Test
    public void verifyIssue() {
        SonarXmlCheckVerifier.verifyIssues("IssueMapper.xml", check);
    }
}