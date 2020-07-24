package com.jd.sonar.java.itqa.plugin.checks.other;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidPatternCompileInMethodRuleTest {

    @Test
    public void test(){
        JavaCheckVerifier.verify("src/test/files/other/AvoidPatternCompileInMethodCheck.java", new AvoidPatternCompileInMethodRule());
    }

}
