
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyClass {

    static Map<String,Integer> map1 = new HashMap<String,Integer>(16);
    static Map<String,String> map2 = new ConcurrentHashMap<String,String>(16);
    static List<String> list1 = new ArrayList(16);

    static {
        for(String key : map1.keySet()){
            int value = map1.get(key);      // Noncompliant
            String value2 = "Hello";
            if(list1.get(0).equals(key)){
                // doSomthing
            }
        }

        for(String key : map1.keySet()){
            String value2 = "Hello";
            if(list1.get(0)==key){
                // doSomthing
            }
        }

        for(String key : map1.keySet()){
            String value2 = "Hello";
            //doSomething
            if(map1.get(key)==value2){      // Noncompliant
                // doSomething
            }
        }

        for(Map.Entry<String,String> entry:map2.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            //doSomething
        }

    }

    private void compliantMethod() {

        for(String key : map1.keySet()){
            String value2 = "Hello";
            //doSomething
            if(list1.get(0)==key){
                // doSomthing
            }
        }

        for(Map.Entry<String,String> entry:map2.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            //doSomething
        }

    }

    private void noncompliantMethod() {

        for(String key : map2.keySet()){
            String value = map2.get(key);       // Noncompliant
            //doSomething
        }

        for(String key : map1.keySet()){
            int value = map1.get(key);          // Noncompliant
            if(map1.get(key)==value2){          // Noncompliant
                // doSomething
            }
        }
    }

}