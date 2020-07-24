package com.jd.sonar.java.itqa.plugin.checks.concurrent;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * @author: yangshuo8
 * @date: 2019-02-25 17:16
 * @desc: description
 */
public class AvoidCallStaticSimpleDateFormatRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/concurrent/AvoidCallStaticSimpleDateFormatCheck.java", new AvoidCallStaticSimpleDateFormatRule());
    }
}