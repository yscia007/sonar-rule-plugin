package com.jd.sonar.test.file;

import com.jd.netdisk.dao.capacity.CapacityStatisticalDao;
import org.jd.utils.Abs;
import org.sonar.plugins.java.api.tree.ClassTree;

public interface A {}

public class test {

    private CapacityStatisticalDao capacityStatisticalDao;

    public static String funcA(String a, String b) {
        return Abs.funcA(a, b);
    }

    public static Integer funcA(Integer a, Integer b) {
        return Abs.funcA(a, b);
    }

    @Override
    public CapacityStatistical getCapacityStatistical() {  // Compliant
        return capacityStatisticalDao.getCapacityStatistical();
    }

    public static Boolean isPojo(String className) {
        return className != null && className.matches("regex");
    }

    public static Boolean isPojo(ClassTree tree) {
        return tree != null && tree.simpleName() != null && isPojo(tree.simpleName().name());
    }

    public static int testFibonacci(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }else { // Noncompliant@+1
            return testFibonacci(n-1) + testFibonacci(n-2);// Noncompliant
        }
    }

    public static int testFibonacci(int a, int b) {
        if (a == 1 || b == 1) {
            return 1;
        }else {
            return testFibonacci(a-1) + testFibonacci(b-2);
        }
    }
    public int func(int i) {
        if (i == 1) {
            return 2;
        }  // Noncompliant@+1
        return func(i -1) * func(i + 1); // Noncompliant
    }

    public String func(String x) {
        return String.valueOf(func(10));
    }

    public String func(String arg1, String arg2) {
        return func(arg1) + func(arg2);
    }
}