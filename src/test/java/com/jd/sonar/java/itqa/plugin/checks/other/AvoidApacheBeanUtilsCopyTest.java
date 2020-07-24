package com.jd.sonar.java.itqa.plugin.checks.other;

import com.jd.sonar.java.itqa.plugin.checks.other.AvoidApacheBeanUtilsCopyRule;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidApacheBeanUtilsCopyTest {

    @Test
    public void ApacheBeanUtilsCopyTest() {
        JavaCheckVerifier.verify("src/test/files/other/AvoidApacheBeanUtilsCopyCheck/ApacheBeanUtilsCopy.java", new AvoidApacheBeanUtilsCopyRule());
    }

    @Test
    public void SpringBeanUtilsCopyTest() {
        JavaCheckVerifier.verify("src/test/files/other/AvoidApacheBeanUtilsCopyCheck/SpringBeanUtilsCopy.java", new AvoidApacheBeanUtilsCopyRule());
    }


}
