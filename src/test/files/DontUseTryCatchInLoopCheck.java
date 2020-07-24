package com.jd.sonar.test.file;

public class DontUseTryCatchInForEachCircleCheck{

    public void function() {

        int i = 0;
        for (; i++; i < 10) {
            try {                             // Noncompliant
                int sum;
                sum = sum + i;
            } catch (Exception e) { }
        }

        for (Object i : list) {
            try {                             // Noncompliant
                int sum;
                sum = sum + i;
            } catch (Exception e) { }
        }

        do {
            try {                             // Noncompliant
                int sum;
                sum = sum + i;
            } catch (Exception e) { }
        } while (i < 20);

        while (i < 20) {
            try {                             // Noncompliant
                int sum;
                sum = sum + i;
            } catch (Exception e) { }
        }

        try {                   // Compliant
            for (i = 1; i <= 100; i++) {
                int sum;
                sum = sum + i;
            }
        } catch (Exception e) { }

        try {                   // Compliant
            do {
                int sum;
                sum = sum + i;
            } while (i<20);
        } catch (Exception e) { }


        try {                   // Compliant
            while (i<20){
                int sum;
                sum = sum + i;
            }
        } catch (Exception e) { }


    }



}