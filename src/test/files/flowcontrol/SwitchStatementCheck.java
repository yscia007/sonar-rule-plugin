//public class Example {
//    public void fn() {
//        int i;
//        switch (i) {   // Noncompliant
//            case 0:
//                break;
//            case 1:
//                int j;
//                switch (j) {
//                    case 0:
//                        break;
//                    default:
//                        return;
//                }
//                break;
//        }
//    }
//}
//
//public class Foo {
//    public void bar() {
//        int i;
//        switch (i) {
//            case 0:
//                break;
//            case 1:
//                int j;
//                switch (j) {  // Noncompliant
//                    case 0:
//                        break;
//                }
//                break;
//            default:
//                return;
//        }
//    }
//}
//
//public class Foo1 {
//    public void bar() {
//        int i;
//        switch (i) {
//            case 0:     // Noncompliant
//                int j =1;
//            case 1:
//                break;
//            case 2:
//                continue;
//            case 3:
//            default:
//                int k = 1;
//                return;
//        }
//    }
//}
//
//public class Foo2 {
//    public void bar() {
//        int i;
//        switch (i) {
//            case 0:
//                int j =1;
//                break;
//            case 1:
//            case 2:
//                continue;
//            default:
//        }
//    }
//}
//
//public class Foo3 {
//    public void bar() {
//        int i;
//        switch (i) {     // Noncompliant
//            case 0:
//            case 1:
//            case 2:
//                break;
//        }
//    }
//}
//
//class A {
//    private A(boolean test) {
//        if (test)
//            throw new Exception();
//    }
//    private void f(byte b, char c) {
//        int myVariable = 0;
//        switch (myVariable) {
//            case 0:
//            case 1: // Noncompliant
//                System.out.println("Test");
//            case 2: // Compliant
//                break;
//            case (8 | 2): // Noncompliant
//                System.out.println("Test");
//            case 3: // Compliant
//                return;
//            case 4: // Compliant
//                throw new IllegalStateException();
//            case 5: // Noncompliant
//                System.out.println();
//            default: // Noncompliant
//                System.out.println();
//            case 6: // Noncompliant
//                int a = 0;
//            case 8: { // Compliant
//                if (false) {
//                    break;
//                } else {
//                    break;
//                }
//            }
//            case 12: // Compliant
//                try {
//                    return new A(true);
//                } catch (Exception e) {
//                    throw new RuntimeException("Wrapping", e);
//                }
//            case 13: // Noncompliant
//                try {
//                    return new A(true);
//                } catch (Exception e) {
//                    System.out.println("Error");
//                }
//            case 14: // Noncompliant
//                try {
//                    int i = i / b;
//                } catch (Exception e) {
//                    throw new RuntimeException("Wrapping", e);
//                }
//            case 9: // Compliant
//            default:
//        }
//        for (int i = 0; i < 1; i++) {
//            switch (myVariable) {
//                case 0: // Compliant
//                    continue; // belongs to for loop
//                case 1:
//                    break;
//                default:
//            }
//        }
//
//        switch (myVariable) {
//            case 0: // Noncompliant
//                switch (myVariable) {
//                    case 0:
//                    case 1: // Noncompliant
//                        System.out.println();
//                        switch (myVariable){    // Noncompliant
//                            case 0:
//                            case 1:
//                                break;
//                        }
//                    case 2: // Compliant
//                        break;
//                    default:
//                }
//                System.out.println();
//            case 1: // Compliant
//                switch (myVariable) {
//                    case 0:
//                    case 1: // Compliant
//                        System.out.println();
//                        switch (myVariable){  // Noncompliant
//                            case 0: // Noncompliant
//                                System.out.println();
//                            case 1:
//                                break;
//                        }
//                        break;
//                    case 2: // Compliant
//                        break;
//                    default:
//                }
//                break;
//            case 2: // Compliant
//            default:
//        }
//
//        switch(myVariable) {  // Noncompliant
//        }
//
//        switch (b) {
//            case (byte) 0: // Compliant
//                break;
//            case (byte) 1: // Noncompliant
//                System.out.println("Test");
//            case 2: // Compliant
//                break;
//            case 3: // Noncompliant
//                System.out.println("Test 2");
//            case 4:
//                break;
//            default:
//        }
//
//        switch (c) {
//            case 'c': // Compliant
//                break;
//            case 'a': // Noncompliant
//                System.out.println("Test");
//            case 'd': // Compliant
//                break;
//            case 'e': // Noncompliant
//                System.out.println("Test 2");
//            case 'x':
//                break;
//            default:
//        }
//    }
//}
//
//class S128 {
//    void compliantFlows() {
//        int i = 0;
//        switch (i) {
//            case 0:
//                System.out.println("OK");
//                break;
//            case 1: // Noncompliant
//                System.out.println("No floor ):");
//            default:
//                System.out.println("ERROR");
//                break;
//            case 2:
//                System.out.println("WARN");
//                break;
//            default:
//        }
//    }
//}
//
//class Conditions {
//    public void test(int j) {
//        int i = 0;
//        switch (i) {
//            case 0: // Compliant
//                if (j == 0) {
//                    break;
//                } else {
//                    break;
//                }
//            case 1: { // Compliant
//                if (j != 0) {
//                    break;
//                } else {
//                    break;
//                }
//            }
//            case 2: // Compliant
//                if (j == 0)
//                    break;
//                else
//                    break;
//            case 3: { // Compliant
//                if (j != 0)
//                    break;
//                else
//                    break;
//            }
//            case 4: // Noncompliant
//                if (j == 0)
//                    break;
//            case 5: // Compliant
//                break;
//            case 6: // Noncompliant
//                if (j == 0) {
//                    System.out.println(j);
//                } else {
//                    break;
//                }
//            case 8:
//                break;
//            default: // Noncompliant
//                if (j == 1)
//                    break;
//            case 9:
//                break;
//            case 7: // Noncompliant
//                if (j == 0)
//                    System.out.println(j);
//                else
//                    break;
//            case 12: // Compliant
//                System.out.println(j);
//            default:
//        }
//    }
//
//    private fallThroughComments(int i) {
//        switch (i) {
//            case 1:
//                System.out.println(i);
//                // fall-through
//            case 2:
//                // FALL-THROUGH
//                System.out.println(i);
//            case 3:
//                // fallthrough
//                System.out.println(i);
//            case 4:
//                //$FALL-THROUGH$
//                System.out.println(i);
//            case 5:
//                // fallthru
//                System.out.println(i);
//            case 6:
//                //falls-through
//                System.out.println(i);
//            case 7:
//                System.out.println(i); //fall through
//            case 8:
//                System.out.println("foo");
//                break;
//            default:
//        }
//    }
//}
//
//class NestedSwitches {
//    public enum E {
//        A, B
//    }
//    public int test1(String s, E e, int i) {
//        switch (s) {
//            case "FOO":
//                return 0;
//            case "BAR": // Compliant - all cases in the following switch have unconditional termination statements
//                switch (e) {
//                    case A:
//                        switch (i) {
//                            case A:
//                            case B:
//                                return 1;
//                            default:
//                                return 2;
//                        }
//                    case B:
//                        return 2;
//                    default:
//                        throw new IllegalArgumentException();
//                }
//            default:
//                throw new IllegalArgumentException();
//        }
//    }
//
//    public int test2(String s, E e) {
//        switch (s) {
//            case "FOO":
//                return 0;
//            case "BAR": // Noncompliant
//                switch (e) {  // Noncompliant
//                    case A:
//                        return 1;
//                    case B:
//                        return 2;
//                }
//            default:
//                throw new IllegalArgumentException();
//        }
//        return 0;
//    }
//
//    public int test3(String s, E e) {
//        int result = 0;
//        switch (s) {
//            case "FOO":
//                return 0;
//            case "BAR": // Noncompliant
//                switch (e) { // Noncompliant
//                    case A: // Noncompliant
//                        result = 0;
//                    case B:
//                        return 2;
//                }
//            default:
//                throw new IllegalArgumentException();
//        }
//        return result;
//    }
//}
//
//class SwitchesAndLoops {
//    public enum E {
//        A, B
//    }
//    private void nestedSwitchesAndLoops(String s, E e) {
//        int result = 0;
//
//        for (int i = 0; i < 1; i++) {
//            switch (myVariable) {
//                case 0:
//                    continue label1;
//                case 1:
//                    switch (s) {
//                        case "FOO":
//                            for (int j = 0; j < 1; j++) {
//                                switch (j) {
//                                    case 0:
//                                    case 1:
//                                        throw new IllegalArgumentException();
//                                    case 2:
//                                        continue;
//                                    case 3: // Noncompliant
//                                        label1:
//                                        result = 0;
//                                    case 4:
//                                        result = 1;
//                                        // fallthrough
//                                    case 5: // Noncompliant
//                                        result = 1;
//                                    case 6:
//                                    case 7:
//                                    case 8:
//                                        result = 6;
//                                    default:
//                                }
//                                continue;
//                            }
//                            continue;
//                            return 0;
//                        case "BAR":
//                            switch (e) {  // Noncompliant
//                                case A: // Noncompliant
//                                    result = 0;
//                                case B:
//                                    return 2;
//                            }
//                        default:
//                    }
//                    break;
//                case 2:
//                    result = 2;
//                    continue;
//                default:
//                    result = 0;
//            }
//        }
//    }
//
//    public void infiniteLoopCheck() {
//        int value = 0;
//        switch (value) {  // Noncompliant
//            case 0: // Noncompliant
//                for (int i = 0; i < 1; i++) { }
//            case 1:
//        }
//    }
//}
//
//





public enum MailNoticeEnum {

    //录取通知书
    ADMISSION((byte)0x01,"聘用意向书","发送聘用意向书成功","发送聘用意向书失败","mail/admission/admission.vm","mail/admission/admission_trainee.vm","mail/admission/admission_monthly.vm"),
    //笔试
    WRITTEN_EXAMINATION((byte)0X02,"笔试通知","笔试通知成功","笔试通知失败","mail/written_examination/written_examination.vm","mail/written_examination/written_examination.vm","mail/written_examination/written_examination.vm"),
    //一面
    FIRST_INTERVIEW((byte)0x03,"面试通知","一面通知成功","一面通知失败","mail/interview/interview.vm","mail/interview/interview.vm","mail/interview/interview.vm"),
    //二面
    SECOND_INTERVIEW((byte)0x04,"面试通知","二面通知成功","二面通知失败","mail/interview/interview.vm","mail/interview/interview.vm","mail/interview/interview.vm"),
    //三面
    THIRD_INTERVIEW((byte)0x05,"面试通知","二面通知成功","二面通知失败","mail/interview/interview.vm","mail/interview/interview.vm","mail/interview/interview.vm"),
    //面试
    INTERVIEW((byte)0x06,"面试通知","面试通知成功","面试通知失败","mail/interview/interview.vm","mail/interview/interview.vm","mail/interview/interview.vm");


    MailNoticeEnum(Byte type, String subject, String success,
                   String error,String devPath,String traineePath,String monthlyPath){
        this.type = type;
        this.subject = subject;
        this.success = success;
        this.error = error;
        this.devPath = devPath;
        this.traineePath = traineePath;
        this.monthlyPath = monthlyPath;
    }

    private final Byte type;

    private final String subject;

    private final String success;

    private final String error;

    /**
     * 研发类
     */
    private final String devPath;

    /**
     * 管培生类
     */
    private final String traineePath;

    /**
     * 月薪的路径
     */
    private final String monthlyPath;

    public Byte getType() {
        return type;
    }

    public String getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }


    public static String getCustomError(byte type){
        switch(type){
            case 1:
                return ADMISSION.getError();
            case 2:
                return WRITTEN_EXAMINATION.getError();
            case 3:
                return FIRST_INTERVIEW.getError();
            case 4:
                return SECOND_INTERVIEW.getError();
            case 5:
                return THIRD_INTERVIEW.getError();
            case 6:
                return INTERVIEW.getError();
        }
        return "系统异常";
    }


    public static String getCustomPath(byte type,byte category){
        switch(type){
            case 1:
                if(category == 1) {
                    return ADMISSION.getMonthlyPath();
                }else if(category == 2) {
                    return ADMISSION.getDevPath();
                }else if(category == 3) {
                    return ADMISSION.getTraineePath();
                }
            case 2:
                if(category == 1) {
                    return WRITTEN_EXAMINATION.getMonthlyPath();
                }else if(category == 3) {
                    return WRITTEN_EXAMINATION.getTraineePath();
                }
            case 3:
                if(category == 1) {
                    return FIRST_INTERVIEW.getMonthlyPath();
                }else if(category == 3) {
                    return FIRST_INTERVIEW.getTraineePath();
                }
            case 4:
                if(category == 1) {
                    return SECOND_INTERVIEW.getMonthlyPath();
                }else if(category == 3) {
                    return SECOND_INTERVIEW.getTraineePath();
                }
            case 5:
                if(category == 1) {
                    return THIRD_INTERVIEW.getMonthlyPath();
                }else if(category == 3) {
                    return THIRD_INTERVIEW.getTraineePath();
                }
            case 6:
                if(category == 1) {
                    return INTERVIEW.getMonthlyPath();
                }else if(category == 3) {
                    return INTERVIEW.getTraineePath();
                }
        }
        return "";
    }



    public String getSubject() {
        return subject;
    }

    public String getDevPath() {
        return devPath;
    }

    public String getTraineePath() {
        return traineePath;
    }

    public String getMonthlyPath() {
        return monthlyPath;
    }
}
