package com.jd.sonar.java.itqa.plugin.checks.comment;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ClassMustHaveAuthorRuleTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/comment/ClassMustHaveAuthorRuleCheck.java", new ClassMustHaveAuthorRule());
    }
}
