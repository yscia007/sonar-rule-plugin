

import java.util.regex.Pattern;
public class PatternCompile {
    // precompile regex
    private static Pattern NUMBER_PATTERN = Pattern.compile("[0-9]+"); // Compliant
    private static final Pattern[] SPARK_JOB_IDS_PATTERNS = { Pattern.compile("Submitted application (application[0-9_]*)") };
}

public class PatternCompileA {
    public void getNumberPattern() {
        // Avoid define Pattern.compile in method body
        Pattern localPattern = Pattern.compile("[0-9]+");   // Noncompliant
        boolean flag = true;
        if (flag) {
            Pattern localPattern1 = Pattern.compile("[0-9]+");  // Noncompliant
        }
        Pattern p = Pattern.compile("[一二两三四五六七八九123456789]万[一二两三四五六七八九123456789](?!(千|百|十))"); // Noncompliant
        p = Pattern.compile("[一二两三四五六七八九123456789]千[一二两三四五六七八九123456789](?!(百|十))");  // Noncompliant
    }

    public static boolean isChineseCharacter(final char ch) {
        String string = String.valueOf(ch);
        return Pattern.compile("[\u4e00-\u9fa5]").matcher(string).find();   // Noncompliant
    }
}

public class PatternCompileC {
    public void getNumberPattern(String number) {
        // define Pattern.compile in method body
        Pattern localPattern = Pattern.compile(number); // Compliant
    }
}

