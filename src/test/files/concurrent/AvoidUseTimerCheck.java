

import java.util.Timer;

/**
 * @author caikang
 * @date 2016/11/15
 */
public class TimerTest {
    private Timer timer = new Timer(); // Noncompliant
    private Timer timer1; // Noncompliant

    public void test(){
        Timer t = new Timer(); // Noncompliant
        new Timer(); // Noncompliant
    }
    public void func() {
        timer1 = new Timer(); // Noncompliant
    }
}