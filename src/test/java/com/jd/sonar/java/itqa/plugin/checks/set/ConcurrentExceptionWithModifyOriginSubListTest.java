package com.jd.sonar.java.itqa.plugin.checks.set;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ConcurrentExceptionWithModifyOriginSubListTest {
    @Test
    public void testScanFile() {
        JavaCheckVerifier.verify("src/test/files/set/ConcurrentExceptionWithModifyOriginSubListCheck.java", new ConcurrentExceptionWithModifyOriginSubListRule());
    }
}
