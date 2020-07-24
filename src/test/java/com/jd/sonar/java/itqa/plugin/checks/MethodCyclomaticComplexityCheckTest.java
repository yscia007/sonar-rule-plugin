package com.jd.sonar.java.itqa.plugin.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * @author: yangshuo8
 * @date: 2019-04-18 11:10
 * @desc: description
 */
public class MethodCyclomaticComplexityCheckTest {

    @Test
    public void defaults() {
        JavaCheckVerifier.verifyNoIssue("src/test/files/methodComplexityCheck/NoIssue.java", new MethodCyclomaticComplexityCheck());
    }

    @Test
    public void test() {
        MethodCyclomaticComplexityCheck check = new MethodCyclomaticComplexityCheck();
        check.setMax(1);
        JavaCheckVerifier.verify("src/test/files/methodComplexityCheck/NonCompliant.java", check);
    }
}