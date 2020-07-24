package com.jd.sonar.java.itqa.plugin.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import static org.assertj.core.api.Assertions.assertThat;

public class AvoidIfElseNestingMoreThanThreeRuleTest {
    @Test
    public void detected() {
        AvoidIfElseNestingMoreThanThreeRule check = new AvoidIfElseNestingMoreThanThreeRule();
        assertThat(check.max).isEqualTo(3);
        JavaCheckVerifier.verify("src/test/files/AvoidIfElseNestingMoreThanThreeCheck.java", check);
    }
}
