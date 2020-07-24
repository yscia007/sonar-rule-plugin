package com.jd.sonar.java.itqa.plugin.checks.performance;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidUseRecursiveFunctionRuleTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/performance/AvoidUseRecursiveFunctionCheck.java", new AvoidUseRecursiveFunctionRule());
    }
}
