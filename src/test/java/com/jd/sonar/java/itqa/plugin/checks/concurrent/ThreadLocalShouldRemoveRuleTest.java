package com.jd.sonar.java.itqa.plugin.checks.concurrent;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import static org.junit.Assert.*;

public class ThreadLocalShouldRemoveRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/concurrent/ThreadLocalShouldRemoveCheck.java", new ThreadLocalShouldRemoveRule());
    }
}