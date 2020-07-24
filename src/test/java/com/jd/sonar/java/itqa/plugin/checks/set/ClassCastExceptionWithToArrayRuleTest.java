package com.jd.sonar.java.itqa.plugin.checks.set;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

import static org.junit.Assert.*;

/**
 * @author: yangshuo8
 * @date: 2019-02-19 18:09
 * @desc: description
 */
public class ClassCastExceptionWithToArrayRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/set/ClassCastExceptionWithToArrayCheck.java", new ClassCastExceptionWithToArrayRule());
    }
}