
/*
 * Created on 18 nov. 2004
 */

package com.alibaba.pmd.test;

// following import is important

class PMDProjectPropertyPage extends PropertyPage {
    private Table buildAvailableRulesTableViewer(final Composite parent) {
        return ruleTable;
    }
}


public class CommentsMustBeJavadocFormat {

    /**
     * name
     */
    private String name;

    //2323
    private String name2; // Noncompliant

    /**
     * return name
     */
    public void getName() {}
}

public class CommentsMustBeJavadocFormat1 {
    private String name;
    public void getName() {}
}

// a comment
// a comment
public class CommentsMustBeJavadocFormat2 {// Noncompliant
    // a comment
    private String name; // Noncompliant
    // a comment
    public void getName() {} // Noncompliant
}

/**
 * test
 */
public class CommentsMustBeJavadocFormat3 {
    /**
     * name
     */
    private String name;

    /**
     * return name
     */
    public void getName() {}
}

// comment of outer class
public class Outer_class { // Noncompliant
    // comment of method of outer class
    public void test() {// Noncompliant
        // comment of anonymous inner class
        AnonymousInner inner = new AnonymousInner() {
            // comment of method in anonymous inner class
            public void mymethod() {
                System.out.println("This is an example of anonymous inner class");
            }
        };
        inner.mymethod();
    }
}

public class CommentsMustBeJavadocFormat4 {
    private String name;
    private Integer age;
    public void getName() {
    }
}

public enum TestEnum {
    // comment
    MONDAY, TUESDAY, THURSDAY  // Noncompliant
        }

