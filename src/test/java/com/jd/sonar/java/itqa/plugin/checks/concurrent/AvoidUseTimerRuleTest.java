package com.jd.sonar.java.itqa.plugin.checks.concurrent;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * @author: yangshuo8
 * @date: 2019-02-26 14:10
 * @desc: description
 */
public class AvoidUseTimerRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/concurrent/AvoidUseTimerCheck.java", new AvoidUseTimerRule());
    }
}