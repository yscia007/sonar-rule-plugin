package com.jd.sonar.test.file;

import com.thoughtworks.xstream.XStream;


public class myJavaTestClass {
    private String str = "";
    public String xxxx(String a, String b) {
        try {
            if ("".equals(str)) {
                String var1 = "";
                XStream xStream = new XStream(); // Noncompliant {{应将XStream变量 "xStream" 定义为全局变量,在方法体内定义为局部变量后频繁初始化对性能影响较大。}}
                String var2 = "test";
                str = "abcde";
            }
            return var2;
        }
    }

    public String yyyy(String a, String b) {
        String var1 = "";
        XStream xStream = new XStream(); // Noncompliant {{应将XStream变量 "xStream" 定义为全局变量,在方法体内定义为局部变量后频繁初始化对性能影响较大。}}
        String var2 = "test";
        str = "abcde";
        return var2;
    }

    public String func(String a, String b) {
        String var1 = "";
        for (int i = 0; i< 100; i++) {
            XStream xStream = new XStream();
        }
        String var2 = "test";
        str = "abcde";
        return var2;
    }
}