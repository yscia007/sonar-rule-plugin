public class MyClass {

    static String regex = "\\||;|\\.|$";

    static {

        //String[] splitResult = para1.split("\\||;|\\.|$");     // Noncompliant

        String[] splitResult1 = para1.split(regex);     // Noncompliant

        for(int i=0;i<para2.split("\\||;|\\.|\\$").length;i++){
            String[] aa = para3.split("\\||;|\\.|\\$");
        }

        if (a>b){
            String[] aa = para4.split("||;|\\.|\\$");      // Noncompliant
        }

    }

    private void compliantMethod() {

        for(int i=0;i<para2.split("\\|").length;i++){
            String[] aa = para3.split("\\.");
        }

        String[] splitResult1 = para1.split("\\||;|\\.|\\|");

    }

    private void noncompliantMethod() {

        String[] splitResult1 = para1.split("\\|||;|\\.|\\|");     // Noncompliant

        if (a>b){
            String[] aa = para4.split("*|.");      // Noncompliant
        }

        String[] splitResult2 = para1.split("$");     // Noncompliant
    }

}