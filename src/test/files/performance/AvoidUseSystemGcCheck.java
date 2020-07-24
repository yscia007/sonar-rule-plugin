

class A {
    private void f() {
        System.gc(); // Noncompliant
        foo.gc(); // Compliant
        System.exit(0); // Compliant
        System.gc; // Compliant
        System.gc[0]; // Compliant
        Runtime.getRuntime().gc(); // Noncompliant
        Runtime.getFoo().gc();
        Runtime.getRuntime(foo).gc();
        Foo.getRuntime().gc();
        foo().getRuntime().gc();
        foo().gc();
        (foo()).gc();
    }
}