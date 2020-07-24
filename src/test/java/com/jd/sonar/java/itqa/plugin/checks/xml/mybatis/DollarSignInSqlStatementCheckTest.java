package com.jd.sonar.java.itqa.plugin.checks.xml.mybatis;

import org.junit.Test;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheck;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

/**
 * @author: yangshuo8
 * @date: 2019-04-03 18:47
 * @desc: description
 */
public class DollarSignInSqlStatementCheckTest {

    private static final SonarXmlCheck check = new DollarSignInSqlStatementCheck();

    @Test
    public void verifyIssue() {
        SonarXmlCheckVerifier.verifyIssues("IssueMapper.xml", check);
    }

    @Test
    public void verifyNoIssue() {
        SonarXmlCheckVerifier.verifyNoIssue("/NoIssueMapper.xml", check);
    }
}