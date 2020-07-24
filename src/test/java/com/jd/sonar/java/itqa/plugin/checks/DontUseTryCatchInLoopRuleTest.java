package com.jd.sonar.java.itqa.plugin.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class DontUseTryCatchInLoopRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/DontUseTryCatchInLoopCheck.java", new DontUseTryCatchInLoopRule());
    }
}
