

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.ResourceTransactionManager;
import org.springframework.transaction.support.SimpleTransactionStatus;

/**
 * @author caikang
 * @date 2017/03/29
 */
public class TestTransactional {
    private ResourceTransactionManager manager;

    @Transactional
    @Override
    public void test(){    // Noncompliant
        //manager.rollback(new SimpleTransactionStatus());
    }
}

public class TestTransactional11 {
    private ResourceTransactionManager manager;

    @Transactional
    public void test(){
        manager.rollback(new SimpleTransactionStatus());
    }
}

public class TestTransactional22 {
    private ResourceTransactionManager manager;

    @Transactional(rollbackFor = Exception.class)
    public void test(){
        //manager.rollback(new SimpleTransactionStatus());
    }
}

@Transactional   // Noncompliant
public class TestTransactional33 {
    private ResourceTransactionManager manager;

    public void test(){
        //manager.rollback(new SimpleTransactionStatus());
    }
}

@Transactional(rollbackFor = Exception.class)
@Slf4j
public class TestTransactional44 {
    private ResourceTransactionManager manager;

    public void test(){
        //manager.rollback(new SimpleTransactionStatus());
    }
}

@Transactional   // Noncompliant
public class TestTransactional55 {

    private ResourceTransactionManager manager;

    public void test(){
        manager.rollback(new SimpleTransactionStatus());
    }
}