package com.jd.sonar.java.itqa.plugin.checks.concurrent;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class CountDownShouldInFinallyTest {
    @Test
    public void testScanFile() {
        JavaCheckVerifier.verify("src/test/files/concurrent/CountDownShouldInFinallyCheck.java", new CountDownShouldInFinallyRule());
    }

}
