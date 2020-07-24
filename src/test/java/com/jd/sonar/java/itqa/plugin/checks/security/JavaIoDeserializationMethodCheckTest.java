package com.jd.sonar.java.itqa.plugin.checks.security;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * @author: yangshuo8
 * @date: 2019-04-18 17:45
 * @desc: description
 */
public class JavaIoDeserializationMethodCheckTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/security/DeserializationMethodReadObjectCheck.java", new JavaIoDeserializationMethodCheck());
    }
}