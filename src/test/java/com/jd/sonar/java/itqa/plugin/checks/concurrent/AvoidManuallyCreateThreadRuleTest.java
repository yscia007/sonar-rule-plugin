package com.jd.sonar.java.itqa.plugin.checks.concurrent;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangshuo8
 * @date: 2019-02-01
 * @time: 10:03
 * @desc: description
 */
public class AvoidManuallyCreateThreadRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/concurrent/AvoidManuallyCreateThreadCheck.java", new AvoidManuallyCreateThreadRule());
    }
}