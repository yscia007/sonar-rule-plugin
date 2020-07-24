package com.jd.sonar.java.itqa.plugin.checks.constant;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class UpperEllRuleTest {
    @Test
    public void test(){
        JavaCheckVerifier.verify("src/test/files/constant/UpperEllCheck.java", new UpperEllRule());
    }
}
