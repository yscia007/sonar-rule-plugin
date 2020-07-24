package com.jd.sonar.java.itqa.plugin.checks.flowcontrol;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class NeedBraceRuleTest {

    @Test
    public void test(){
        JavaCheckVerifier.verify("src/test/files/flowcontrol/NeedBraceCheck.java", new NeedBraceRule());
    }
}
