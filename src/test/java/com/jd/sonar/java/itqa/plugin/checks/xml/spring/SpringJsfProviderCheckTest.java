package com.jd.sonar.java.itqa.plugin.checks.xml.spring;

import org.junit.Test;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheck;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

/**
 * @author: yangshuo8
 * @date: 2019-10-28 11:47
 * @desc: description
 */
public class SpringJsfProviderCheckTest {

    private static final SonarXmlCheck check = new SpringJsfProviderCheck();

    @Test
    public void verifyProviders() {
        SonarXmlCheckVerifier.verifyIssues("SpringJsfConfig.xml", check);
    }
}