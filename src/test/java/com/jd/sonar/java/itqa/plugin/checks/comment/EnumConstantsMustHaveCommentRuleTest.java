package com.jd.sonar.java.itqa.plugin.checks.comment;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class EnumConstantsMustHaveCommentRuleTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/comment/EnumConstantsMustHaveCommentRuleCheck.java", new EnumConstantsMustHaveCommentRule());
    }
}
