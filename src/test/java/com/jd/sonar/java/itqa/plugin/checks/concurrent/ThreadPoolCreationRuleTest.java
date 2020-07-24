package com.jd.sonar.java.itqa.plugin.checks.concurrent;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangshuo8
 * @date: 2019-01-30
 * @time: 12:00
 * @desc: description
 */
public class ThreadPoolCreationRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/concurrent/ThreadPoolCreationCheck.java", new ThreadPoolCreationRule());
    }
}