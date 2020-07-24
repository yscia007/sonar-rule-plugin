package com.jd.sonar.java.itqa.plugin.checks.exception;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * @author: yangshuo8
 * @date: 2019-03-01 09:31
 * @desc: description
 */
public class TransactionMustHaveRollbackRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/exception/TransactionMustHaveRollbackCheck.java", new TransactionMustHaveRollbackRule());
    }
}