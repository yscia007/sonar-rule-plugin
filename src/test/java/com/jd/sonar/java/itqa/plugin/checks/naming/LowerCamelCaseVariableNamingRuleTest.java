package com.jd.sonar.java.itqa.plugin.checks.naming;

import org.junit.Test;

import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * @author: lina51
 * @date: 2019-02-20 16:08
 * @desc: description
 */
public class LowerCamelCaseVariableNamingRuleTest {

    @Test
    public void test() {
        LowerCamelCaseVariableNamingRule check = new LowerCamelCaseVariableNamingRule();
        check.format = "^[a-z|$][a-z0-9]*([A-Z][a-z0-9]+)*(DO|DTO|VO|DAO)?$";
        //check.format = "^I?([A-Z][a-z0-9]+)+(([A-Z])|(DO|DTO|VO|DAO|BO|DAOImpl|YunOS|AO|PO))?$";
        JavaCheckVerifier.verify("src/test/files/naming/LowerCamelCaseVariableNamingCheck.java", check);
    }

    @Test
    public void testIssue() {
        JavaCheckVerifier.verify("src/test/files/naming/LowerCamelCaseCheck.java", new LowerCamelCaseVariableNamingRule());
    }
}