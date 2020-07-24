

public class MyClass {
    public MyClass() {} // Noncompliant
}

class ParallelTask<T> implements Callable {
    private Function<T> function;
    private Execution<T> execution;
    private LoginContext context;
    ParallelTask(Function<T> function, Execution<T> execution) { // Noncompliant
        this.function = function;
        this.execution = execution;
        this.context = LoginContext.getLoginContext();
    }
    @Override
    public T call() {
        try {
            execution.lock();
            //1.设置线程本地变量
            LoginContext.setLoginContext(this.context);
            //2.执行任务
            return function.apply();
        }catch (Throwable e){
            execution.fail(e);
            return null;
        }finally {
            //3.latch-1，清空线程本地变量
            LoginContext.remove();
            execution.unlock();
        }
    }
}

public class PagedListBean<T> implements PagedList<T> {
    public static final int DEFAULT_PAGESIZE = 30;
    private final int page;
    private final int pageSize;
    private final int itemCount;
    private final int pageCount;
    private final boolean countRequired;
    private final List<T> underly;

    public PagedListBean(int page, int itemCount) { // Noncompliant
        this(page, itemCount, DEFAULT_PAGESIZE, true);
    }  // Noncompliant
}

