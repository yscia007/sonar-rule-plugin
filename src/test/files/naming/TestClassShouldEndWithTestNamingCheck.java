
package com.jd.sonar.java.itqa.plugin.checks.naming;

class A { // Noncompliant
    @Test
    void foo() {}
}

class ATest { // Compliant
    @org.testng.annotations.Test
    void foo() {}
}

class B { // Compliant
    void foo() {}
    A a = new A() {};
}
class TestA {
    @Test
    void foo() {
    }
}
class ATestCase {
    @Test
    void foo() {
    }
}

class AnITCase {
    @Test
    void foo() {
    }
}

class JUnit5Noncompliant1 { // Noncompliant
    @org.junit.jupiter.api.Test
    void foo() {}
}

class JUnit5Noncompliant2 { // Noncompliant
    @org.junit.jupiter.api.RepeatedTest(2)
    void foo() {}
}
