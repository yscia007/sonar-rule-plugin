package com.jd.sonar.java.itqa.plugin.checks.xml.spring;

import org.junit.Test;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheck;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

/**
 * @author: yangshuo8
 * @date: 2019-03-27 16:37
 * @desc: description
 */
public class SpringDefaultLazyInitCheckTest {

    private static final SonarXmlCheck check = new SpringDefaultLazyInitCheck();

    @Test
    public void verifyIssue() {
        SonarXmlCheckVerifier.verifyIssues("enableDefaultLazyInit/applicationContext.xml", check);
    }

    @Test
    public void verifyNoIssue() {
        SonarXmlCheckVerifier.verifyNoIssue("disableDefaultLazyInit/applicationContext.xml", check);
    }
}