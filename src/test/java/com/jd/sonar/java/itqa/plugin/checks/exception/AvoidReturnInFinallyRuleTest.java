package com.jd.sonar.java.itqa.plugin.checks.exception;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidReturnInFinallyRuleTest {

    @Test
    public void test(){
        JavaCheckVerifier.verify("src/test/files/exception/AvoidReturnInFinallyCheck.java", new AvoidReturnInFinallyRule());
    }
}
