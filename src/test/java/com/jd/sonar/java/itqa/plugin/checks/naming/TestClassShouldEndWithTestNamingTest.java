package com.jd.sonar.java.itqa.plugin.checks.naming;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class TestClassShouldEndWithTestNamingTest {
    @Test
    public  void test(){
        JavaCheckVerifier.verify("src/test/files/naming/TestClassShouldEndWithTestNamingCheck.java", new TestClassShouldEndWithTestNamingRule());
    }

}
