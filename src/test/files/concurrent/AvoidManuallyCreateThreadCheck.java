package com.jd.sonar.java.itqa.test.concurrent;

import java.util.concurrent.ThreadFactory;

/**
 * @author caikang
 * @date 2016/11/15
 */
public class ThreadCreateTest {
//    static {
//        new Thread();
//        int a = 1;
//    }
//
//    private Thread thread1 = new Thread();  // Noncompliant

    private Thread createThread() {
        return new Thread(); // Noncompliant
    }

    public void testParamter() {
        Thread thread2 = new Thread(); // Noncompliant

    }
}



public class ThreadCreateRightTest {
    {
        Runtime.getRuntime().addShutdownHook(
                new Thread()
        );
    }
    private ThreadFactory threadFactory = r -> {
        Thread thread1 = new Thread(r);
        thread1.setName("kdsljf");
        return thread1;
    };

    private ThreadFactory threadFactory1 = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread();
            thread.setName("xxx");
            return thread;
        }
    };

    private ThreadFactory threadFactory2 = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread();
                    return thread;
                }
            }.newThread(r);
        }
    };

    public void testNewThread(){
        new ThreadFactory(){

            @Override
            public Thread newThread(Runnable r) {
                return new Thread();
            }
        }.newThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public void testParamter(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 100, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(),
                r -> {
                    Thread thread1 = new Thread(r);
                    thread1.setName("xxx");
                    return thread1;
                });
        new ThreadPoolExecutor(10, 10, 100, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(),
                r -> {
                    Thread thread1 = new Thread(r);
                    thread1.setName("xxx");
                    return thread1;
                });
        new ThreadPoolExecutor(10, 10, 100, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread();
                    }
                });
    }
}

class MyThreadFactory implements ThreadFactory {
    /**
     * Constructs a new {@code Thread}.  Implementations may also initialize
     * priority, name, daemon status, {@code ThreadGroup}, etc.
     *
     * @param r a runnable to be executed by new thread instance
     * @return constructed thread, or {@code null} if the request to create a thread is rejected
     */
    @Override
    public Thread newThread(Runnable r) {
        return new Thread();
    }
}
