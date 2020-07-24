package com.jd.sonar.java.itqa.plugin.checks.naming;

import com.jd.sonar.java.itqa.plugin.checks.naming.ExceptionClassShouldEndWithExceptionRule;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ExceptionClassShouldEndWithExceptionRuleTest {

    @Test
    public void test(){
        JavaCheckVerifier.verify("src/test/files/naming/ExceptionClassNameCheck.java", new ExceptionClassShouldEndWithExceptionRule());
    }
}
