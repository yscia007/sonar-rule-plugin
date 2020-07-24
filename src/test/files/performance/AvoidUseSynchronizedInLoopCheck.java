



class A {
    public void func(int a) {}
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

class B {
   private A a;
    public synchronized void f1(){}
    public void f2(){}
    public synchronized void f3(){}
    public void f4() {}
    public void testA() {
       a.f3();
       for (int i = 0; i < 10; i++) {
           a.f1(); // Noncompliant
           a.f4();
           f1(); // Noncompliant
       }
       while (true) {
           a.f4();
           a.f1(); // Noncompliant
           f3(); // Noncompliant
       }
       do {
           a.f1(); // Noncompliant
           a.f4();
       } while (true);
       a.f3();
   }
   public void testB() {
       for (int i = 0; i < 10; i++) {
           a.f2();
           a.f3();   // Noncompliant
           for (int j = 0; j < 100; j++) {
               a.f1(); // Noncompliant
               a.f4();
           }
       }
   }
}