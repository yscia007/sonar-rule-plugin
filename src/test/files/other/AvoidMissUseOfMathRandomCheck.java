

import java.util.Random;

public class Test {
    public static void main( String args[] ){
        int i = (int)(Math.random() * 10);   // Noncompliant
        System.out.println("Next int value: " + i);

        long j = (long)(Math.random() * 250);  // Noncompliant
        System.out.println("Next long value: " + j);
    }
}

public class Test1 {
    public static void main( String args[] ){
        double d = Math.random() * 10;   // Compliant

        Random random = new Random();
        int k = random.nextInt();
    }
}