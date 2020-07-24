package com.jd.sonar.java.itqa.plugin.checks.xml.mybatis;

import org.junit.Test;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheck;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

/**
 * @author: yangshuo8
 * @date: 2020-06-16 14:25
 * @desc: description
 */
public class SqlStatementProbeCheckTest {

    private SonarXmlCheck check = new SqlStatementProbeCheck();

    @Test
    public void verifyIssue() {
        SonarXmlCheckVerifier.verifyIssues("IssueMapper.xml", check);
    }
}