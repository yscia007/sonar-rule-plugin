package com.jd.sonar.java.itqa.plugin.checks.oop;

import com.jd.sonar.java.itqa.plugin.checks.oop.PojoMustOverrideToStringRule;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class PojoMustOverrideToStringTest {

    @Test
    public void test(){
        JavaCheckVerifier.verify("src/test/files/oop/PojoMustOverrideToStringCheck.java", new PojoMustOverrideToStringRule());
    }
}

