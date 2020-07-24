public class FooDO {
    private static final int age;
    private String tom;
    private boolean isBar;				// Noncompliant
    private byte[] buffer;
    private Long pageSize = 20;

    public void bar() {
        int pageNo = 1;
    }

    private static class NestedDTO {
        private String tom;
        private boolean isBar;			// Noncompliant
    }

    private static class InnerClass {
        private boolean isBar;
    }
}