package com.jd.sonar.java.itqa.plugin.checks.concurrent;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * @author: yangshuo8
 * @date: 2019-02-22 09:49
 * @desc: description
 */
public class ThreadShouldSetNameRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/concurrent/ThreadShouldSetNameCheck.java", new ThreadShouldSetNameRule());
    }
}