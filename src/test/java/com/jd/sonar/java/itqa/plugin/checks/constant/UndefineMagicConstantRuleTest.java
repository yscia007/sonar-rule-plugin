package com.jd.sonar.java.itqa.plugin.checks.constant;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * @author: yangshuo8
 * @date: 2019-02-26 15:32
 * @desc: description
 */
public class UndefineMagicConstantRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/constant/UndefineMagicConstantCheck.java", new UndefineMagicConstantRule());
    }
}