public class Foo {
    private String $1;             // Noncompliant
    private String _1;             // Noncompliant
    private String ee$;             // Noncompliant
    private String ee_;             // Noncompliant
    public void $sayHello(){}     // Noncompliant
    public void _sayHello(){}      // Noncompliant
    public void sayHello$(){}     // Noncompliant
    public void sayHello_(){}      // Noncompliant

}

public interface $Test { // Noncompliant
    public Integer _abc; // Noncompliant
}

public enum _Test {} // Noncompliant

public class TestClass$ {} // Noncompliant

public class AvoidStartWithDollarAndUnderLineNamingRuleTest {
    private String s;
    public void sayHello(){}
}