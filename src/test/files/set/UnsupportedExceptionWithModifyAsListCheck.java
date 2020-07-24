public class Foo {
    private void method(long aLong) {
        List<String> t = Arrays.asList("a","b","c");
    }
}

public class Moo {
    private void method1() {
        List<String> list1 = Arrays.asList("a", "b", "c");
        list1.add("test"); // Noncompliant
        if (true) {
            List<String> list = Arrays.asList("a", "b", "c");
            list.add("d");       // Noncompliant
            list.remove("22");   // Noncompliant {{这里使用"remove"可能会导致UnsupportedOperationException}}
            list.clear();        // Noncompliant
            list1.clear();       // Noncompliant
        }
        List<String> list2 = new ArrayList<String>();
        list2.add("b");
        list2.remove("b");
        list1.clear(); // Noncompliant
    }

    private void method2() {
        if (true) {
            List<String> list1 = new ArrayList<String>();
            List<String> list = Arrays.asList("a", "b", "c");
            list.add("d");        // Noncompliant
            list1.add("test");
        }
        List<String> list2 = new ArrayList<String>();
        list2.add("b");
    }

    private void method3() {
        List<String> list1 = new ArrayList<String>();
        list1.add("test");
    }
}