
import java.util.ArrayList;

public class MyClass {

    static List<String> list1 = new ArrayList<String>(6);

    static {
        if(list1.containsAll(list1)){       // Noncompliant
            //doSomething
        }
        list1.removeAll(list1);             // Noncompliant
        list1.remove(list1);
        list1.addAll(list1);
    }

    private void nonCompliantMethod() {
        if(list1.containsAll(list1)){       // Noncompliant
            //doSomething
        }
        list1.removeAll(list1);             // Noncompliant
    }

    private void compliantMethod() {
        list1.remove(list1);
        list1.addAll(list1);
    }
}