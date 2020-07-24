
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyClass {

    int MAX_VALUE = 100;
    static ArrayList<Integer> list1 = new ArrayList<>(16);
    static ArrayList<Integer> list2 = new ArrayList<>(16);
    static Set<String> set1 = new HashSet<>(16);
    static String aa="Hello world";

    static {
        for(int i = 0; i <= MAX_VALUE; i++) {
            // 时间复杂度 O(n)
            list1.contains(i);                  // Noncompliant
        }

        do {
            MAX_VALUE --;
            if(list2.contains(MAX_VALUE)){      // Noncompliant
                //doSomething
            }
        }while (MAX_VALUE>=0);

        while (MAX_VALUE>=0){
            if(set1.contains(MAX_VALUE)){
                //doSomething
            }
            MAX_VALUE --;
        }
    }

    private void compliantMethod() {
        Set<Integer> set = new HashSet(list1);
        for (int i = 0; i <= MAX_VALUE; i++) {
            if(set.contains(i)){
                //doSomething
            };
            set.contains(i);
            aa.contains(i);
        }
    }

    private void noncompliantMethod() {
        while (MAX_VALUE>=0){
            if(list1.contains(MAX_VALUE)){      // Noncompliant
                //doSomething
            }
            MAX_VALUE --;
        }

        Set<Integer> set = new HashSet(list1);
        for (int i = 0; i <= MAX_VALUE; i++) {
            if(set.contains(i)){
                //doSomething
            };
            list2.contains(i);                  // Noncompliant

        }
    }

    private boolean checkSendPayWithMutilValues(String sendPay, int digits, String expectValue){
        if(StringUtils.isNotBlank(sendPay) && sendPay.length() >= digits){
            //期望值与目标值比较
            String sendPayValue = sendPay.substring(digits-1, digits);
            for(int i=0;i<100;i++){
                if(expectValue.contains("#".concat(sendPayValue).concat("#"))){
                    return true;
                }
            }
        }
        return false;
    }

}