package com.jd.sonar.java.itqa.plugin.checks.oop;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;


/**
 * @author: yangshuo8
 * @date: 2019-02-27 14:06
 * @desc: description
 */
public class WrapperTypeEqualityRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/oop/WrapperTypeEqualityCheck.java", new WrapperTypeEqualityRule());
    }
}