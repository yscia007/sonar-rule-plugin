public class BooleanPropertyNamingRuleTestDO{
    private boolean issuccess;          // Noncompliant
    private boolean issuccess1;         // Noncompliant
    private boolean success;
    public void sayHello (int i) {
        boolean issuccess1;
        boolean success1;
    }
}

public class BooleanPropertyNamingRuleTest{
    private boolean success;
    public void sayHello (int i) {
        boolean success1;
    }
}

public class ProcessBuilder {
    public static final boolean isWindows = isWindows();
}

public class APPInfoVO implements java.io.Serializable{
    private boolean issuccess;   // Noncompliant
}