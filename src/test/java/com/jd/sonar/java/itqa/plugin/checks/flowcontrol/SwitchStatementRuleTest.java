package com.jd.sonar.java.itqa.plugin.checks.flowcontrol;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class SwitchStatementRuleTest {

    @Test
    public void test(){
        JavaCheckVerifier.verify("src/test/files/flowcontrol/SwitchStatementCheck.java", new SwitchStatementRule());
    }


}
