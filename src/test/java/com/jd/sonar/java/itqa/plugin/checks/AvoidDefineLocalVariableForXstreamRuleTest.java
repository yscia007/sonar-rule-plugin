package com.jd.sonar.java.itqa.plugin.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangshuo27
 * @date: 2019/1/15
 * @time: 下午4:05
 * @desc: description
 */
public class AvoidDefineLocalVariableForXstreamRuleTest {

    @Test
    public void testScanFile() {
        JavaCheckVerifier.verify("src/test/files/AvoidDefineLocalVariableForXStreamTypeCheck.java", new AvoidDefineLocalVariableForXstreamRule());
    }
}
