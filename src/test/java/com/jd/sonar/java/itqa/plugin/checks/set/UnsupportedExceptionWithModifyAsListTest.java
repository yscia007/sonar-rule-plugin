package com.jd.sonar.java.itqa.plugin.checks.set;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class UnsupportedExceptionWithModifyAsListTest {

    @Test
    public void test(){
        JavaCheckVerifier.verify("src/test/files/set/UnsupportedExceptionWithModifyAsListCheck.java", new UnsupportedExceptionWithModifyAsListRule());
    }
}
