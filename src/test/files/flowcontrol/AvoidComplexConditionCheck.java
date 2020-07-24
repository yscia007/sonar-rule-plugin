

public class Example {
    public int fn1(int a, int b, int c) {
        if (a == 0 || (b != 0 && c > 0)) {             // Noncompliant
            return 1;
        } else if (a == 0 || b != 0 && c > 0){     // Noncompliant
            return 0;
        } else if (((b !=0 && c > 0))||a>0){       // Noncompliant
            return 2;
        } else if ((a == 0 || b != 0) && (c > 0 && c < 10)) { // Noncompliant

        }
    }

    public int fn2(int a, int b, int c) {
        return (a == 0 || b != 0 && c > 0) ? 1 : 0;   // Noncompliant
    }

    public int fn3(int a, int b) {
        return (a == 0 || b != 0) ? 1 : 0;   // Compliant
    }
}

public class ExampleA {
    public int fn3(int a, int b, int c) {
        boolean flag = (a == 0 || b != 0 && c > 0);
        return flag ? 1 : 0;
    }
}

public class ExampleB {
    public int fn() {
        List<Integer> ids = new ArrayList<>();
        if(!ids.isEmpty()) {
        }
        if (ids.isEmpty() || ids.size() == 0) { // Compliant
        }
        if (!ids.isEmpty() && ids.size() > 5 && ids.size() < 20) { // Noncompliant
        }
        if (ids.size() > 0) {
        }
    }
}

