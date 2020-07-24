package com.jd.sonar.java.itqa.plugin.checks.naming;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * @author: yangshuo8
 * @date: 2019-02-13 16:08
 * @desc: description
 */
public class ConstantFieldShouldBeUpperCaseRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/naming/ConstantFieldShouldBeUpperCaseCheck.java", new ConstantFieldShouldBeUpperCaseRule());
    }
}