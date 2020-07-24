

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author caikang
 * @date 2016/11/25
 */
public class SimpleDateFormatRule {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dateFormatTest = new SimpleDateFormat("yyyy-MM-dd");
    private Lock lock = new ReentrantLock();

    private void test(){
        String string = dateFormat.format(new Date()); // Noncompliant
        String str = dateFormatTest.format(new Date());
        synchronized (this){
            dateFormat.format(new Date());
        }
        dateFormat.format(new Date()); // Noncompliant
        lock.lock();
        try {
            dateFormat.format(new Date());
            dateFormat.format(new Date());
        }finally {
            lock.unlock();
        }
        dateFormat.format(new Date()); // Noncompliant
        lock.lock();
        dateFormat.format(new Date());
        try{
            dateFormat.format(new Date());
        }finally{
            lock.unlock();
        }
    }

    private synchronized void test1(){
        dateFormat.format(new Date());
    }

    private void test2(){
        dateFormat.format(new Date()); // Noncompliant
    }
}

public class SimpleDateFormatRuleNoIssue {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Lock lock = new ReentrantLock();

    private void test(){
        synchronized (this){
            dateFormat.format(new Date());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.format(new Date());
        lock.lock();
        try {
            dateFormat.format(new Date());
            dateFormat.format(new Date());
        }finally {
            lock.unlock();
        }
    }

    private synchronized void test1(){
        dateFormat.format(new Date());
    }
}

public class SimpleDateFormatRuleTest {

    private static final ThreadLocal<DateFormat> DATE_FORMATTER = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public void test() {
        dateFormat.format(new Date()); // Noncompliant
    }
}
