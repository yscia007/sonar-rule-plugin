class CognitiveComplexityCheck {


    public int ternaryOp(int a, int b) { // Noncompliant [[sc=16;ec=25;secondary=6,8]] {{请重构方法 "ternaryOp" 并将方法的认知复杂度从 2 降低为允许的最大值 0。}}

        int c = a>b?b:a;

        return c>20?4:7;

    }

    public boolean extraConditions() { // Noncompliant {{请重构方法 "extraConditions" 并将方法的认知复杂度从 3 降低为允许的最大值 0。}}
        return a && b || foo(b && c);
    }
    public boolean extraConditions2() { // Noncompliant {{请重构方法 "extraConditions2" 并将方法的认知复杂度从 2 降低为允许的最大值 0。}}
        return a && (b || c) || d;
    }
    public void extraConditions3() { // Noncompliant {{请重构方法 "extraConditions3" 并将方法的认知复杂度从 3 降低为允许的最大值 0。}}
        if (a && b || c || d) {}
    }
    public void extraConditions4() { // Noncompliant {{请重构方法 "extraConditions4" 并将方法的认知复杂度从 5 降低为允许的最大值 0。}}
        if (a && b || c && d || e) {}
    }
    public void extraConditions5() { // Noncompliant {{请重构方法 "extraConditions5" 并将方法的认知复杂度从 5 降低为允许的最大值 0。}}
        if (a || b && c || d && e) {}
    }
    public void extraConditions6() {// Noncompliant {{请重构方法 "extraConditions6" 并将方法的认知复杂度从 3 降低为允许的最大值 0。}}
        if (a && b && c || d || e) {}
    }
    public void extraConditions7() {// Noncompliant {{请重构方法 "extraConditions7" 并将方法的认知复杂度从 1 降低为允许的最大值 0。}}
        if (a) {}
    }
    public void extraConditions8() {// Noncompliant {{请重构方法 "extraConditions8" 并将方法的认知复杂度从 2 降低为允许的最大值 0。}}
        if (a && b && c && d && e) {}
    }
    public void extraConditions9() {// Noncompliant {{请重构方法 "extraConditions9" 并将方法的认知复杂度从 2 降低为允许的最大值 0。}}
        if (a || b || c || d || e) {}
    }
    public void extraCondition10() { // Noncompliant {{请重构方法 "extraCondition10" 并将方法的认知复杂度从 4 降低为允许的最大值 0。}}
        if (a && b && c || d || e && f){}
    }


    public void switch2(){ // Noncompliant [[sc=17;ec=24;secondary=46,50,51,51,51,55,57]] {{请重构方法 "switch2" 并将方法的认知复杂度从 12 降低为允许的最大值 0。}}

        switch(foo){                              //+1
            case 1:
                break;
            case ASSIGNMENT:
                if (lhs.is(Tree.Kind.IDENTIFIER)) {   //+2 (nesting=1)
                    if (a && b && c || d) {             //+5 (nesting=2)

                    }

                    if(element.is(Tree.Kind.ASSIGNMENT)) { //+3 (nesting=2)
                        out.remove(symbol);
                    } else {                               //+1
                        out.add(symbol);
                    }
                }
                break;
        }
    }

    public void extraCondition11() { // Noncompliant {{请重构方法 "extraCondition11" 并将方法的认知复杂度从 2 降低为允许的最大值 0。}}
        if (a || (b || c)) {}
    }

    public void extraConditions12() { // Noncompliant [[secondary=70,72,74,76,78,80,81]] {{请重构方法 "extraConditions12" 并将方法的认知复杂度从 7 降低为允许的最大值 0。}}
        if (     // +1
                a
                        && b   // +1 - secondary on each first operator of a new sequence
                        && c
                        || d   // +1
                        || e
                        && f   // +1
                        && g
                        || (h  // +1
                        || (i
                        && j   // +1
                        || k)) // +1 - parentheses completely ignored
                        || l
                        || m
        ){}
    }

    public void breakWithLabel(java.util.Collection<Boolean> objects) { // Noncompliant {{请重构方法 "breakWithLabel" 并将方法的认知复杂度从 2 降低为允许的最大值 0。}}
        doABarrelRoll:
        for(Object o : objects) { // +1
            break doABarrelRoll;    // +1
        }
    }

    public void doFilter(ServletRequest servletRequest) { // Noncompliant {{请重构方法 "doFilter" 并将方法的认知复杂度从 13 降低为允许的最大值 0。}}

        if (consumedByStaticFile) {                             // 1
            return;
        }

        try {

        } catch (HaltException halt) {                          // 1

        } catch (Exception generalException) {                  // 1

        }

        if (body.notSet() && responseWrapper.isRedirected()) {  // 2
            body.set("");
        }

        if (body.notSet() && hasOtherHandlers) {                // 2
            if (servletRequest instanceof HttpRequestWrapper) {   // 2 (nesting=1)
                ((HttpRequestWrapper) servletRequest).notConsumed(true);
                return;
            }
        }

        if (body.notSet() && !externalContainer) {               // 2
            LOG.info("The requested route [" + uri + "] has not been mapped in Spark");
        }

        if (body.isSet()) {                                      // 1
            body.serializeTo(httpResponse, serializerChain, httpRequest);
        } else if (chain != null) {                              // 1
            chain.doFilter(httpRequest, httpResponse);
        }
    }


    public final T to(U u) { // Noncompliant {{请重构方法 "to" 并将方法的认知复杂度从 7 降低为允许的最大值 0。}}

        for (int ctr=0; ctr<args.length; ctr++)
            if (args[ctr].equals("-debug"))
                debug = true ;

        for (int i = chain.length - 1; i >= 0; i--)
            result = chain[i].to(result);

        if (foo)
            for (int i = 0; i < 10; i++)
                doTheThing();

        return (T) result;
    }


    static boolean enforceLimits(BoundTransportAddress boundTransportAddress) { // Noncompliant {{请重构方法 "enforceLimits" 并将方法的认知复杂度从 1 降低为允许的最大值 0。}}
        Iterable<JoinTuple> itr = () -> new JoinTupleIterator(tuples.tuples(), parentIndex, parentReference);

        Predicate<TransportAddress> isLoopbackOrLinkLocalAddress = t -> t.address().getAddress().isLinkLocalAddress()
                || t.address().getAddress().isLoopbackAddress();

    }

    String bulkActivate(Iterator<String> rules) { // Noncompliant {{请重构方法 "bulkActivate" 并将方法的认知复杂度从 6 降低为允许的最大值 0。}}

        try {
            while (rules.hasNext()) {  // +1
                try {
                    if (!changes.isEmpty()) {  }  // +2, nesting 1
                } catch (BadRequestException e) { }  // +2, nesting 1
            }
        } finally {
            if (condition) {  // +1
                doTheThing();
            }
        }
        return result;
    }

    private static String getValueToEval( List<String> strings ) { // Noncompliant {{请重构方法 "getValueToEval" 并将方法的认知复杂度从 6 降低为允许的最大值 0。}}

        if (Measure.Level.ERROR.equals(alertLevel) // +1
                && foo = YELLOW) {   // +1
            return condition.getErrorThreshold();
        } else if (Measure.Level.WARN.equals(alertLevel)) {             // 1
            return condition.getWarningThreshold();
        } else {                                                        // 1
            while (true) {                                                // 2 (nesting = 1)
                doTheThing();
            }
            throw new IllegalStateException(alertLevel.toString());
        }
    }

    boolean isPalindrome(char [] s, int len) { // Noncompliant Refactor this method to reduce its Cognitive Complexity from 3 to the 0 allowed.

        if(len < 2)
            return true;
        else
            return s[0] == s[len-1] && isPalindrome(s[1], len-2); // TODO find recursion
    }

    void extraConditions() { // Noncompliant {{请重构方法 "extraConditions" 并将方法的认知复杂度从 10 降低为允许的最大值 0。}}

        if (a < b) {                // 1
            doTheThing();
        }

        if (a == b || c > 3 || b-7 == c) {  // 2
            while (a-- > 0 && b++ < 10) {     // 3 (nesting = 1)
                doTheOtherThing();
            }
        }

        do {                                // 1

        } while (a-- > 0 || b != YELLOW);   // 1 (for ||)

        for (int i = 0; i < 10 && j > 20; i++) {  // 2
            doSomethingElse();
        }
    }

    public static void main (String [] args) { // Noncompliant {{请重构方法 "main" 并将方法的认知复杂度从 4 降低为允许的最大值 0。}}

        Runnable r = () -> {
            if (condition) {
                System.out.println("Hello world!");
            }
        };

        r = new MyRunnable();

        r = new Runnable () {
            public void run(){
                if (condition) {
                    System.out.println("Well, hello again");
                }
            }
        };
    }

    int sumOfNonPrimes(int limit) { // Noncompliant {{请重构方法 "sumOfNonPrimes" 并将方法的认知复杂度从 9 降低为允许的最大值 0。}}

        int sum = 0;
        OUTER: for (int i = 0; i < limit; ++i) {
            if (i <= 2) {
                continue;
            }
            for (int j = 2; j < 1; ++j) {
                if (i % j == 0) {
                    continue OUTER;
                }
            }
            sum += i;
        }
        return sum;
    }

    String getWeight(int i){ // Noncompliant {{请重构方法 "getWeight" 并将方法的认知复杂度从 4 降低为允许的最大值 0。}}

        if (i <=0) {
            return "no weight";
        }
        if (i < 10) {
            return "light";
        }
        if (i < 20) {
            return "medium";
        }
        if (i < 30) {
            return "heavy";
        }
        return "very heavy";
    }

    public static HighlightingType toProtocolType(TypeOfText textType) { // Noncompliant {{请重构方法 "toProtocolType" 并将方法的认知复杂度从 1 降低为允许的最大值 0。}}

        switch (textType) {
            case ANNOTATION: {
                return HighlightingType.ANNOTATION;
            }
            case CONSTANT:
                return HighlightingType.CONSTANT;
            case CPP_DOC:
                return HighlightingType.CPP_DOC;
            default:
                throw new IllegalArgumentException(textType.toString());
        }
    }

    public String getSpecifiedByKeysAsCommaList() {
        return getRuleKeysAsString(specifiedBy);
    }

    void localClasses() { // Noncompliant {{请重构方法 "localClasses" 并将方法的认知复杂度从 3 降低为允许的最大值 0。}}
        class local {
            boolean plop() { // compliant : will be counted in the enclosing method
                return a && b || c && d;
            }
        }
    }

    void noNestingForIfElseIf() { // Noncompliant {{请重构方法 "noNestingForIfElseIf" 并将方法的认知复杂度从 21 降低为允许的最大值 0。}}
        while (true) { // +1
            if (true) { // +2 (nesting=1)
                for (;;) { // +3 (nesting=2)
                    if (true) { // +4 (nesting=3)
                    } else if (true) { // +1
                    } else { // +1
                        if (true) {
                        } // +5 (nesting=4)
                    }

                    if (true) {} // +4 (nesting=3)
                }
            }
        }
    }


}

