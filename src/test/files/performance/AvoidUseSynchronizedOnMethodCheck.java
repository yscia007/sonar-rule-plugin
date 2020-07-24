package com.jd.sonar.test.file;

public class AvoidUseSynchronizedOnMethodRuleCheck {
    public AvoidUseSynchronizedOnMethodRuleCheck(MyClass mc) { }

    int     foo1() { return 0; }
    synchronized void    foo2(int value) { }// Noncompliant
    int     foo3(int value) { return 0; }
    synchronized Object  foo4(int value) { return null; }// Noncompliant
    private static boolean isUsingLock = false;
    public synchronized void operate() {// Noncompliant
        isUsingLock = true;
        // do somthing
        isUsingLock = false;
    }
    private static String name = "";
    public void operate2() {
        synchronized (name)
        {
            if (name.equals(""))  {
                name = "boonya";
            }
        }
    }
    int     foo6(int value, String name) { return 0; }
    int     foo7(int ... values) { return 0;}
}