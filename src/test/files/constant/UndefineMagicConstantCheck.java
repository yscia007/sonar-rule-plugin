

public class Foo {
    private final static  String notWarn = "666l";

    private void method(String path) {
        if (null == path || !path.startsWith("/home/admin/leveldb/") // Noncompliant
                || path.split("/").length <= 5) {
        } else if ("/root/".equals(path)) { // Noncompliant

        }
    }

    public class Foo1 {
        private void method() {
            Integer i = 1;
            Integer k = 0;
            Boolean h = false;
            Long m = 2L;
            String n = "";
            if (i > 2) { // Noncompliant
            }
            if (i > 1) {
            }
            if (m > 1L) {
            }
            if (i != null) {
            }
            if (h != false) {
            }
            if (n.equals("")) {
            }
            if ("abcde".equals(n)) { // Noncompliant
            }
            for (int j = 0; j < 10; i++) {  // Noncompliant
                if (i > 2) {
                }
                if (i != null) {
                }
                if ("abcde".equals(n)) {
                }
            }
            while (k < 1) {
                if (i > 2) {
                }
                k++;
            }
        }
    }
}
