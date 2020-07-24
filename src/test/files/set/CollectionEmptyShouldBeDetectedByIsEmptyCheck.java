
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;


public class MyClass {   //if语句和三段式，需加入三段式正反例

    static Map<String,  String> map1 = new HashMap<String,  String>(16);
    static Map<String,  String> map2 = new ConcurrentHashMap<String,  String>(16);
    static List<String> list1 = new ArrayList<String>(16);
    static Deque<String> deque1 = new ArrayDeque<>(16);
    static Set<String>  set1 = new HashSet<>(16);

    static {

        if(map2.size()==0){     // Noncompliant

            //doSomething
        }
        if(list1.size()==4){

            //doSomething
        }
        if(list1.size()<=0){        // Noncompliant

            //doSomething
        }

        Boolean aa = list1.size()==0?true:false;  // Noncompliant
    }

    private void compliantMethod() {

        if(list1.size()==2){
            //doSomething
        }

        Boolean aa = list1.size()==4?true:false;

    }

    private void noncompliantMethod() {

        if(map1.size()==0){         // Noncompliant
            //doSomething
        }
        if(map2.size()==0){         // Noncompliant
            //doSomething
        }
        if(list1.size()<=0){        // Noncompliant
            //doSomething
        }
        if(deque1.size()>0){       // Noncompliant
            //doSomething
        }
        if(set1.size()== 0){         // Noncompliant
            //doSomething
        }

        Boolean aa = list1.size()==0?true:false;   // Noncompliant

    }

}