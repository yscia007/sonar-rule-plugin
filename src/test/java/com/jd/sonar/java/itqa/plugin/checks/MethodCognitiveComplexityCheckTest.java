package com.jd.sonar.java.itqa.plugin.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * @author: yangshuo8
 * @date: 2019-04-18 14:29
 * @desc: description
 */
public class MethodCognitiveComplexityCheckTest {

    @Test
    public void defaults() {
        JavaCheckVerifier.verifyNoIssue("src/test/files/methodCognitiveComplexityCheck/NoIssue.java",
                new MethodCognitiveComplexityCheck());
    }

    @Test
    public void test() {
        MethodCognitiveComplexityCheck check = new MethodCognitiveComplexityCheck();
        check.setMax(0);
        JavaCheckVerifier.verify("src/test/files/methodCognitiveComplexityCheck/NonCompliant.java", check);
    }
}