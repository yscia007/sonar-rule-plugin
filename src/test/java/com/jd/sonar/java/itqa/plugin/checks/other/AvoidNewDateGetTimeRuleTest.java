package com.jd.sonar.java.itqa.plugin.checks.other;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidNewDateGetTimeRuleTest {

    @Test
    public void testScanFile() {
        JavaCheckVerifier.verify("src/test/files/other/AvoidNewDateGetTimeCheck.java", new AvoidNewDateGetTimeRule());
    }

}
