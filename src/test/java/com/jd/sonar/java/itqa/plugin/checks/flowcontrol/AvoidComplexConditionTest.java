package com.jd.sonar.java.itqa.plugin.checks.flowcontrol;

import com.jd.sonar.java.itqa.plugin.checks.flowcontrol.AvoidComplexConditionRule;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidComplexConditionTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/flowcontrol/AvoidComplexConditionCheck.java", new AvoidComplexConditionRule());
    }
}
