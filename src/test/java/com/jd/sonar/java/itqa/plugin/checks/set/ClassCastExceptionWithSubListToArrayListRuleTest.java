package com.jd.sonar.java.itqa.plugin.checks.set;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;


/**
 * @author: yangshuo8
 * @date: 2019-02-19 15:39
 * @desc: description
 */
public class ClassCastExceptionWithSubListToArrayListRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/set/ClassCastExceptionWithSubListToArrayListCheck.java", new ClassCastExceptionWithSubListToArrayListRule());
    }

}