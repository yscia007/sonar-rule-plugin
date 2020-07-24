package com.jd.sonar.test.file;

public class ClassMustHaveAuthorRule1 {}  // Noncompliant

/**
 * @author keriezhang
 * @date 2017/07/18
 */
public class ClassMustHaveAuthorRule2 {}

/**
 * @date 2016/12/14
 */
public class ClassMustHaveAuthorRule3 {}   // Noncompliant

/**
 * @author keriezhang
 * @date 2016/12/14
 */
public class Outer_Demo {
    public class Inner_Demo {
    }
}

public enum Day1 {    // Noncompliant
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY
}

/**
 * @author keriezhang
 * @date 2016/12/14
 */
public enum Day2 {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY
}

/**
 * @author keriezhang
 * @date 2016/12/14
 */
public class Vehicle1 {
    private String id;
    private String name;
    enum color {
        RED, GREEN, ANY;
    }
}

/**
 * @author keriezhang
 * @date 2016/12/14
 */
public interface Vehicle2 {
    public Number getNumber();
    public void method2();
    public enum color {
        RED, GREEN, ANY;
    }
}

/**
 * @Author keriezhang
 * @date 2016/12/14
 */
public interface Vehicle3 {  // Compliant
    public Number getNumber();
    public void method2();
    public enum color {
        RED, GREEN, ANY;
    }
}
