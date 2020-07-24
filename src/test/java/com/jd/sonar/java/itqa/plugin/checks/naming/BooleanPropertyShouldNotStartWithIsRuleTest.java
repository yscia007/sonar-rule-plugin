package com.jd.sonar.java.itqa.plugin.checks.naming;

import com.jd.sonar.java.itqa.plugin.checks.naming.BooleanPropertyShouldNotStartWithIsRule;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class BooleanPropertyShouldNotStartWithIsRuleTest {
    @Test
    public void testScanFile() {
        JavaCheckVerifier.verify("src/test/files/naming/BooleanPropertyShouldNotStartWithIsCheck.java", new BooleanPropertyShouldNotStartWithIsRule());
    }
}
