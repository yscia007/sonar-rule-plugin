

class Foo {
    private void method() {

        List<String> originList = new ArrayList<String>();
        originList.add("22");
        List<String> ff = new ArrayList<String>();
        for (String item : originList) {
            originList.add("bb");   // Noncompliant
        }
        originList.add("bb");

    }

    private void method(long aLong) {
        List<String> originList = new ArrayList<String>();
        originList.add("22");
        for (String item : originList) {
            originList.add("bb");      // Noncompliant
            originList.remove("cc");   // Noncompliant
            originList.clear();        // Noncompliant
        }
    }
}