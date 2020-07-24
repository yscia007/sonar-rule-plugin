public class FooDO {
    private static final String HELLO = "Hello";
    private String tom = ""; 	      // Noncompliant
    private String jerry;
    private int pageSize = 20;	      // Noncompliant

    public void bar() {
        int pageNo = 1;
    }

    private static class NestedDTO {
        private String tom = "";      // Noncompliant
    }

    private static class InnerClass {
        private String tom = "";
    }
}

public class FooPO {
    private static final String HELLO = "Hello";
    private String tom = "";
    private String jerry;
    private int pageSize = 20;

    public void bar() {
        int pageNo = 1;
    }

    private static class NestedDTO {
        private String tom = "";       // Noncompliant
    }
    private static class InnerClass {
        private String tom = "";
    }
}

public class FooBO {
    private static final String HELLO = "Hello";
    private String tom = ""; 	       // Noncompliant
    private boolean success = false;  // Noncompliant
    private String jerry;
    public int pageSize = 20;

    public void bar() {
        int pageNo = 1;
    }

    private static class NestedDTO {
        private String tom = "";       // Noncompliant
    }
}

public class APPInfoVO implements java.io.Serializable{
    private boolean success = false;  // Noncompliant

}
