public abstract class AbstractClassOrInterfaceMethodMustUseJavadocA {
    public String getName(String firstName, String secondName) throws XPathException, IOException {
        return "lalalala";
    }
}

public abstract class AbstractClassOrInterfaceMethodMustUseJavadocB {
    public abstract String getName(String firstName, String secondName) throws XPathException, IOException;   // Noncompliant
}


public abstract class AbstractClassOrInterfaceMethodMustUseJavadocC{

    // hahahaha
    //sss
    /**
     */
    public abstract String getName(String firstName, String secondName) throws XPathException, IOException;  // Noncompliant
}

public interface AbstractClassOrInterfaceMethodMustUseJavadocJ {
    /**
     * get user name
     *
     * @param firstName first name
     * @return full name
     * @throws  xpath exception
     */
    String getName(String firstName, String secondName) throws XPathException, IOException;  // Noncompliant
}


public abstract class AbstractClassOrInterfaceMethodMustUseJavadocD {
    /**
     * only function comment.
     */
    public abstract void getFistName();

    /**
     * function comment
     *
     * @param firstName first name
     * @param secondName second name
     * @return full name
     * @throws  xpath exception
     * @throws  IO exceptioin
     */
    public abstract String getName(String firstName, String secondName) throws XPathException, IOException;
}

public interface AbstractClassOrInterfaceMethodMustUseJavadocE {
    String getName(String firstName, String secondName) throws XPathException, IOException;    // Noncompliant
}

public interface AbstractClassOrInterfaceMethodMustUseJavadocF {
    /**
     */
    void getName();   // Noncompliant
}


public interface AbstractClassOrInterfaceMethodMustUseJavadocG {
    /**
     */
    String getName(String firstName, String secondName) throws XPathException, IOException;  // Noncompliant
}

public interface AbstractClassOrInterfaceMethodMustUseJavadocH {
    /**
     * get user name
     *
     * @param firstName first name
     * @param secondName second name
     * @return full name
     * @throws  xpath exception
     * @throws  io exception
     */
    String getName(String firstName, String secondName) throws XPathException, IOException;
}

public interface InterfaceTest {
    int test();        // Noncompliant

    int test2();       // Noncompliant

    public enum CmdbAttributeName {
        schema("schema"), planId("planId");

        private String prototype;

        CmdbAttributeName(String prototype){
            this.prototype = prototype;
        }

        public String getPrototype() {
            return this.prototype;
        }
    }

    public class InnerClass {
        public void classTest() {
        }
    }


    String getName(String username);  // Noncompliant
}

public interface Stage {
    /**
     * test
     */
    void test();   // 注释没问题

    /**
     * Process Stage
     *
     * @param context context of responsibility chain
     */
    void process(PipeLineContext context);

    /**
     * whether this stage can be skipped, true by default.
     * @return  boolean  can skip or not.
     */
    default boolean isCanSkip() {
        return true;
    }
}