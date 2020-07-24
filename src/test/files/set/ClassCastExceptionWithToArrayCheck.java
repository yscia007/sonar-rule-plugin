

public class Foo {
    private void method1(long aLong) {
        List<Integer> list = new ArrayList<Integer>(16);
        Integer[] b = (Integer [])list.toArray(new Integer[list.size()]); // Compliant
    }

    private void method2(long aLong) {
        Integer[] a = (Integer [])c.toArray(); // Noncompliant
    }

    public void method3() {
        List<String> list = new ArrayList<String>(2);
        list.add("guan");
        list.add("bao");
        String[] array = new String[list.size()];
        array = list.toArray(array); // Compliant
    }
}