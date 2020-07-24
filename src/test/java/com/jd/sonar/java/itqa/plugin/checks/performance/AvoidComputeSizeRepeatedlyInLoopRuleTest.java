package com.jd.sonar.java.itqa.plugin.checks.performance;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: xuxiao200
 * @date: 2019/1/21
 * @time: 下午17:02
 * @desc: description
 */
public class AvoidComputeSizeRepeatedlyInLoopRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/performance/AvoidComputeSizeRepeatedlyInLoopCheck.java", new AvoidComputeSizeRepeatedlyInLoopRule());
    }
}