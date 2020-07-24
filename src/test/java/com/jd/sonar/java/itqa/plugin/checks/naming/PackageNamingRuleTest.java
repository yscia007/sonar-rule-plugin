package com.jd.sonar.java.itqa.plugin.checks.naming;

import com.jd.sonar.java.itqa.plugin.checks.naming.PackageNamingRule;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;


public class PackageNamingRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/naming/package/BadPackageNameNonCompliant.java", new PackageNamingRule());
    }

    @Test
    public void test1() {
        PackageNamingRule check = new PackageNamingRule();
        check.format = "^[a-zA-Z0-9]*$";
        JavaCheckVerifier.verifyNoIssue("src/test/files/naming/package/BadPackageName.java", check);
    }

    @Test
    public void test2() {
        JavaCheckVerifier.verify("src/test/files/naming/package/BadQualifiedIdentifierPackageName.java", new PackageNamingRule());
    }

}
