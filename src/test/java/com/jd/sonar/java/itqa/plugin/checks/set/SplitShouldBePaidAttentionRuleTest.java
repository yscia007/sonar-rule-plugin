package com.jd.sonar.java.itqa.plugin.checks.set;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * Created by weiqiao on 2020/4/2.
 */
public class SplitShouldBePaidAttentionRuleTest {

    @Test
    public void test(){
        JavaCheckVerifier.verify("src/test/files/set/SplitShouldBePaidAttentionCheck.java", new SplitShouldBePaidAttentionRule());
    }

}
