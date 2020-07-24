package com.jd.sonar.java.itqa.plugin.checks.set;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * Created by weiqiao on 2020/3/6.
 * @date: 2020-03-06 10:03
 */
public class CollectionEmptyShouldBeDetectedByIsEmptyRuleTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/set/CollectionEmptyShouldBeDetectedByIsEmptyCheck.java", new CollectionEmptyShouldBeDetectedByIsEmptyRule());
    }

}
