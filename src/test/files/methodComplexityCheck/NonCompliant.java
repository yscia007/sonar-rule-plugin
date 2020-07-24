public class HelloWorld {

    public void sayHello() { // Noncompliant [[effortToFix=1;sc=17;ec=25;secondary=3,4]] {{方法 "sayHello" 的圈复杂度为 2 ，超过了允许的最大值 1 ，请降低方法的圈复杂度。}}
        while (false) {
        }
    }

    public void sayHello2() { // Noncompliant [[effortToFix=3;sc=17;ec=26;secondary=8,9,13,14]] {{方法 "sayHello2" 的圈复杂度为 4 ，超过了允许的最大值 1 ，请降低方法的圈复杂度。}}
        while (false) {
        }
        return
                a
                        || b
                        && c;
    }

    public boolean equals(Object o) {
        while (false) {
        }
        return
                a
                        || b
                        && c;
    }

    public int hashCode() {
        while (false) {
        }
        if (
                a
                        || b
                        && c) {
            return 100;
        }
        return 42;
    }

    void lambdaExclusion() {
        Function<String, String> f = s -> {
            if(s.isEmpty()) {
                return s;
            } else if(s.length >= 2) {
                return s;
            }
            return s;
        };
    }

}
