
import java.util.List;

public class Foo {
    private void method() {
        List<String> list = new ArrayList<String>();
    }

    private void method1(long aLong) {
        List<String> list = new ArrayList<String>();
        list.add("22");
        List<String> test = (ArrayList<String>) list.subList(0, 1);  // Noncompliant

        List<String> test1 = (List<String>) list.subList(0, 1);
    }

    private List<String> method2(long aLong) {
        List<String> list = new ArrayList<String>();
        list.add("22");
        return  (ArrayList<String>) list.subList(0, 1);  // Noncompliant
    }
}


