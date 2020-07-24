package com.jd.sonar.java.itqa.plugin.checks.oop;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class EqualsAvoidNullRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/oop/EqualsAvoidNullCheck.java", new EqualsAvoidNullRule());
    }
}
