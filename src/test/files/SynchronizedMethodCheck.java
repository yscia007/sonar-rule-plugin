
class A {
    public synchronized void f1(){}
    public void f2(){}
    public synchronized void f3(){}
    public void f4() {
        f1();
        for (int i = 0; i < 10; i++) {
            f2();
            f3(); // Noncompliant
        }
    }
}