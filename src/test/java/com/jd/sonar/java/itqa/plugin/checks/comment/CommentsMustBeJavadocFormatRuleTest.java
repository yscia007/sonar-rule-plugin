package com.jd.sonar.java.itqa.plugin.checks.comment;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class CommentsMustBeJavadocFormatRuleTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/comment/CommentsMustBeJavadocFormatRuleCheck.java", new CommentsMustBeJavadocFormatRule());
    }
}
