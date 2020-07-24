package com.jd.sonar.java.itqa.plugin.checks.performance;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidUseSystemGcRuleTest {

    @Test
    public  void test(){
        JavaCheckVerifier.verify("src/test/files/performance/AvoidUseSystemGcCheck.java", new AvoidUseSystemGcRule());
    }
}
