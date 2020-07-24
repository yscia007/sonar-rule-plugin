package com.jd.sonar.java.itqa.plugin.checks.xml.maven;

import org.junit.Test;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheckVerifier;

/**
 * @author: yangshuo8
 * @date: 2019-04-01 10:58
 * @desc: description
 */
public class InsecureDependenciesCheckTest {

    private static final InsecureDependenciesCheck check = new InsecureDependenciesCheck();

    @Test
    public void verifyIssue() {
        SonarXmlCheckVerifier.verifyIssues("hasInsecureDependency/pom.xml", check);
    }
}