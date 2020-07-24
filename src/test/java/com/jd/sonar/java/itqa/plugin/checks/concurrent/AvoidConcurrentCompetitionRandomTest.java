package com.jd.sonar.java.itqa.plugin.checks.concurrent;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidConcurrentCompetitionRandomTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/concurrent/AvoidConcurrentCompetitionRandomCheck.java", new AvoidConcurrentCompetitionRandomRule());
    }
}
