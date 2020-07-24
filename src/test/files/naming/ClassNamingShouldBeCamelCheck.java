public class CamelClassNameRuleTest {
    class CamelclassnameRuleTest{}
    class CamelclassNameRuleTest{}
    class camelClassNameRuleTest{}         // Noncompliant
    class camelClassNameruletest{}         // Noncompliant
    class CamelclassNameRuleTestDO{}
    class CamelclassNameRuleTestDODO{}     // Noncompliant
    class CamelclassNameRuleTestdo{}
    class CamelclassNameRuleTestBO{}
    class CamelclassNameRuleTestbO{}
    class CamelclassNameRuleTestYunOS{}
    class CamelclassNameRuleTestyunOS{}   // Noncompliant
    class CamelclassNameRuleTestDDO{}     // Noncompliant
    class ICamelclassNameRuleTestDO{}
    class ICamelclassNameRuleTest{}
    class IDO{}                           // Noncompliant
    class IAO{}                           // Noncompliant
    class IpAO{}
    class CamelclassNameRuleTestAO{}
}

public interface camelClassNameRuleTestCase { // Noncompliant
}

public class AbstractDAOImpl {
}

public class AbstractBO {
}

public class TestBO {
}

public class routingBranchRouteBO extends AbstractBO {  // Noncompliant
}

public enum testEnum {} // Noncompliant

public class RoutingBranchRoutePO{
}

public class Swagger2Config extends WebMvcConfigurerAdapter {  // Noncompliant
}

public class JCloudService {    // Noncompliant
}
