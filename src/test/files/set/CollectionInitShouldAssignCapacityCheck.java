
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


public class Foo {
    Map<String,  String> map = new HashMap<String,  String>(); //compliant
    Deque<String> deque1 = new ArrayDeque<>();

    private void method(long aLong) {
        Map<String,  String> map2 = new HashMap<String,  String>(16);
        Map<String,  String> map3 = new ConcurrentHashMap<String,  String>(16);
        List<String> originList = new ArrayList<String>(2048);
        Deque<String> deque1 = new ArrayDeque<>(16);
    }

    private void method1() {
        Map<String,  String> map4 = new HashMap<String,  String>();  // Noncompliant
        Map<String,  String> map5 = new ConcurrentHashMap<String,  String>(); // Noncompliant
        Deque<String> deque1 = new ArrayDeque<>(); // Noncompliant
    }
}