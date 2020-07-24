

/**
 * @author caikang
 * @date 2016/11/17
 */
public class ThreadNameTest implements ThreadFactory{

    private void test(){
        new ThreadPoolExecutor(10, 10, 100, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>()  // Noncompliant
                , (r, executor) -> System.out.println("xxx"));
        new ThreadPoolExecutor(11, 11, 100, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), // Noncompliant
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

                    }
                });
        new ThreadPoolExecutor(12,12,100,TimeUnit.MILLISECONDS,new SynchronousQueue<>(), // Noncompliant
                Executors.defaultThreadFactory());

    }

    @Override
    public Thread newThread(Runnable r) {
        return null;
    }
}

public class TestScheduledExecutorService {
    public static final ScheduledExecutorService EXECUTOR_SERVICE = new ScheduledThreadPoolExecutor(3); // Noncompliant
}

public class ThreadNameTest11 implements ThreadFactory{

    private void test(){
        new ThreadPoolExecutor(13, 13, 100, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(),r -> { // Compliant
            Thread thread1 = new Thread();
            thread1.setName("xxx");
            return thread1;
        });
        new ThreadPoolExecutor(14, 14, 100, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(),new ThreadNameTest11()); // Compliant
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 100, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), // Compliant
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread();
                    }
                }, (r, executor) -> {

        });
        new ThreadPoolExecutor(10, 10, 100, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), // Compliant
                r -> {
                    Thread thread2 = new Thread();
                    thread2.setName("xxx");
                    return thread2;
                }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });
        new ThreadPoolExecutor(10, 10, 100, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), // Compliant
                r -> {
                    Thread thread3 = new Thread();
                    thread3.setName("xxx");
                    return thread3;
                });
    }

    @Override
    public Thread newThread(Runnable r) {
        return null;
    }
}
