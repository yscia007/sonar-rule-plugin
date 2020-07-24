

class BadLocalVariableName {

    private String TestStr;

    public BadLocalVariableName() {}

    void Method( // Noncompliant
                 int BAD_FORMAL_PARAMETER // Noncompliant
    ) {
        int BAD; // Noncompliant
        int good;

        for (int I = 0; I < 10; I++) {
            int D; // Noncompliant
        }

        for (good = 0; good < 10; good++) {
        }

        try (Closeable BAD_RESOURCE = open()) { // Noncompliant
        } catch (Exception BAD_EXCEPTION) { // Noncompliant
        } catch (Exception E) { // compliant
        } catch (Exception EX) {// Noncompliant
        }
    }

    Object FIELD_SHOULD_NOT_BE_CHECKED = new Object(){
        {
            int BAD; // Noncompliant
            int ab_aa;  // Noncompliant
            int abDO;
        }
    };

    void forEachMethod() {
        int MY_CONSTANT_IS_NOT_A_CONSTANT = 21; // Noncompliant
        final int MY_LOCAL_CONSTANT = 42; // Compliant
        final String MY_OTHER_LOCAL_CONSTANT = "42"; // Compliant
        final Integer MY_ALTERNATE_CONSTANT = Integer.valueOf(42); // Compliant
        for (byte C : "".getBytes()) {
            int D; // Noncompliant
        }
    }


    void webXML(){ // Noncompliant

    }

    void good() {
    }
    void goodBB() { // Noncompliant

    }
    @Override
    void BadButOverrides(){
    }

    @Deprecated
    void Bad2() { // Noncompliant
    }
    void goodDO() {
    }

    public String toString() { //Overrides from object
        return "...";
    }

}

//public class VariableNameRuleTest {
//    private String absCd;
//    private String AbC;    // Noncompliant
//    private String abCd;
//    private String abCdeFghKmi123;
//    private void f(){
//        String s = "test";
//    }
//}
//
//public class PluginConstants {
//    String PLUGIN_ID = "PMDPlugin";   // Noncompliant
//    String RULE = "java/ali-pmd";     // Noncompliant
//    private private static final String CONSTANT_RULE = "constantRuleTest";
//}
