class B {
    private void f() {
        try {
            return; // Compliant
        } catch (Exception e) {
            return; // Compliant
        } finally {
            return; // Noncompliant
        }

        try {
        } finally {
            new Foo() {
                public void foo() {
                    return; // Compliant
                }
            };
        }

        try {
            return; // Compliant
        } finally {
        }
        try {
        } finally {
            try {
            } catch (Exception e) {
                return; // Noncompliant
            }
        }


            try {
                return; // Compliant
            } catch (Exception e) {
                return; // Compliant
            } finally {
                continue; // Noncompliant {{不要在finally块中执行 continue 语句。}}
            }
            try {
                return; // Compliant
            } catch (Exception e) {
                return; // Compliant
            } finally {
                break; // Noncompliant
            }

        try {
            return; // Compliant
        } catch (Exception e) {
            return; // Compliant
        } finally {
            throw new Exception(); // Noncompliant
        }
    }
}

enum A {
    A;

    {
        return;
    }

}

class FPs {

    int i;

    void f() {
        try {

        } finally {
            switch (i) {
                case 1:
                    break; // Compliant
                case 2:
                    return; // Noncompliant
                case 3:
                    throw new RuntimeException(); // Noncompliant
                default:
                    break; // Compliant
            }
            for (;;) {
                if (i > 0) continue; // Compliant
                break; // Compliant
                return; // Noncompliant
                throw new RuntimeException(); // Noncompliant
            }
            while (true) {
                if (i > 0) continue; // Compliant
                break; // Compliant
                return; // Noncompliant
                throw new RuntimeException(); // Noncompliant
            }
            do {
                if (i > 0) continue; // Compliant
                break; // Compliant
                return; // Noncompliant
                throw new RuntimeException(); // Noncompliant
            } while (true);
        }
    }

    void g() {
            try {
                throw new IllegalStateException();
            } finally {
                continue; // Noncompliant
                break; // Noncompliant
            }
    }

    void fp() {
        outer:
            try {
                throw new IllegalStateException();
            } finally {
                while (true) {
                    continue outer; // FN - requires CFG to detect this, but let's not overcomplicate this rule
                    break; // Compliant
                }
            }
    }
}
