package com.jd.sonar.java.itqa.plugin.checks.other;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class avoidMissUseOfMathRandomRuleTest {
    @Test
    public void detected() {
        JavaCheckVerifier.verify("src/test/files/other/AvoidMissUseOfMathRandomCheck.java", new AvoidMissUseOfMathRandomRule());
    }
}
