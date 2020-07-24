public class Foo {
    private final static  String notWarn = "666l";
    private void processUpperEll(long aLong) {
        long good = (4+5*7^66L/7+890) & (88L + 78 * 4);
    }
}

public class Moo {
    private static final long IGNORE = 666l + 666L;       // Noncompliant
    private static final Long notWarn = 666L;
    private void processUpperEll(long aLong) {
        long bad = (4+5*7^66l/7+890)                      // Noncompliant
                & (88l + 78 * 4);                         // Noncompliant
        long good = (4+5*7^66L/7+890) & (88L + 78 * 4);
    }
}