package com.jd.sonar.java.itqa.plugin.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class CompareEnumMemberEqualsRuleTest {

    @Test
    public void test(){
        JavaCheckVerifier.verify("src/test/files/CompareEnumMemberEqualsCheck.java", new CompareEnumMemberEqualsRule());
        JavaCheckVerifier.verifyNoIssueWithoutSemantic("src/test/files/CompareEnumMemberEqualsCheck.java", new CompareEnumMemberEqualsRule());
    }

}
