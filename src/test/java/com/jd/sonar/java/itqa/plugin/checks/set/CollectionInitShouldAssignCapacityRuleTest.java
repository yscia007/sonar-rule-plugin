package com.jd.sonar.java.itqa.plugin.checks.set;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * @author: yangshuo8
 * @date: 2019-02-14 11:17
 * @desc: description
 */
public class CollectionInitShouldAssignCapacityRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/set/CollectionInitShouldAssignCapacityCheck.java", new CollectionInitShouldAssignCapacityRule());
    }
}