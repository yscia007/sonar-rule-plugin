class Example {
    public void fn() {
        int a;
        int b;
        if (a > 0)  b++;     // Noncompliant

    }
}

class SD {
    public void fn() {
        int a = 2;
        int b = 4;

        if (a > 0) b++;			// Noncompliant

        if (a > 0) {
            b++;
        } else if (a > -1) {
            b++;
        } else            // Noncompliant {{else语句缺少大括号。}}
            b--;

        for (a = 0; a < 3; a ++) // Noncompliant
            b--;
        if (a > 0) {
            while (a > 3) a--;  // Noncompliant
            if (a < 2) a++;  // Noncompliant
        }


        while (a > 3) a--;				// Noncompliant
    }
}

class Aq {
    public void fn() {
        int a;
        boolean flag;
        if (a > 0) {
            // do nothing
        }
        else                  // Noncompliant
        flag = true;
    }
}

