import java.util.Date;
public class AvoidNewDateGetTimeRule {
    public void test() {
        long time = new Date().getTime();     // Noncompliant
    }
}

public class AvoidNewDateGetTime {
    public void test() {
        long time = new Date(123L).getTime();
    }
}