

class A {
    public void f1() {
        List list = new ArrayList();
        int length = 10;
        Integer size = 100;
        int a = list.size();
        for (int i = 0; i < length; i++) {}
        for (int i = 0; i < size; i++) {}
        for (int i = 0; i < list.size(); i++) {  // Noncompliant
            for (int j = 0; j < list.size(); j++) { // Noncompliant

            }
        }
        for (;;) {}
        for (int i = 0; i < 10; i++) {}
        do{}while (w < list.size()); // Noncompliant
        while (w < list.size()){} // Noncompliant
        while (w < size){}

    }
}
