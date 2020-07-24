package com.jd.sonar.java.itqa.plugin.checks.performance;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidUseSynchronizedInWhileLoopRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/performance/AvoidUseSynchronizedInLoopCheck.java", new AvoidUseSynchronizedInWhileLoopRule());
    }
}