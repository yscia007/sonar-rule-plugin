import java.util.Random;

public class RandomTest extends Thread {
    private static final Random RANDOM = new Random();

    private void test() {
        RANDOM.nextBoolean();        // Noncompliant

    }
}

public class RandomTestA extends Thread {
    private static Random RANDOM = new Random();

    private void test(){
        RANDOM.nextBoolean();        // Noncompliant
    }
}

public class RandomTestB extends Thread {
    private final Random RANDOM = new Random();

    private void test(){
        RANDOM.nextBoolean();
    }
}

public class RandomTestC extends Thread {
    private static final Random RANDOM = new Random();

    private void test(){
    }
}

public class MathRandomTest extends Thread {
    private void test(){
        double d = Math.random();         // Noncompliant
    }
}

public class MathRandomTestA extends Thread {
    double d = Math.random();

    private void test(){

    }
}