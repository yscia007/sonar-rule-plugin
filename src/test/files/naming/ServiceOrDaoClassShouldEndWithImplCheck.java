


public class ServiceNameRuleTest extends MyDAO implements MyInterface, TestDAO, TestInterface {         // Noncompliant

    class ServiceNameRuleTest11 extends MyService {  // Compliant

    }

    class ServiceNameRuleTest1 implements MyInterface, TestService{       // Noncompliant

    }
    class ServiceNameRuleTest2Impl implements TestService{

    }
    class ServiceNameRuleTest3Imp implements TestDAO{   // Noncompliant

    }
}
public interface ServiceTest extends TestDAO, B, C {}

public class ServiceNameRuleTestImpl implements TestDAO{

}

public abstract class ServiceNameRuleTestKo implements TestDAO{

}


public abstract class BaseServiceMock<Mapper, Record, Example> implements BaseService<Record, Example> {

}

public class FmsConcurrentReferenceHashMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {

}

private class EntrySet extends AbstractSet<Map.Entry<K, V>> {

}

private static final class SoftEntryReference<K, V> extends SoftReference<Entry<K, V>> implements Reference<K, V> {

}
