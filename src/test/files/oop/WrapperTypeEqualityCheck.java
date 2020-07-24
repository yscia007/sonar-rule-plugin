

public class Foo {
    private static final int x = 3;

    public static Connection openConnection(String url, String userName, String password, DatabaseType type) {
        Connection conn = null;
        try {
            if (type == null) {}
            if (type == DatabaseType.ORACLE) {

            }
        }
    }

    public void bar() {
        Integer a = 2;
        int b = 2;
        Integer c = 3;

        if (a == b) {
            // do nothing
        }
        if (a == c) {	// Noncompliant
            // do nothing
        }
        if (a != null) {
            // do nothing
        }
        if (a == -6) {
            // do nothing
        }
        if (a == x) {
            // do nothing
        }
        if (a == Integer.MAX_VALUE) {
        }
         // PMD can not resolve type of Inner.FLAG
        if (a == Inner.FLAG) { // Noncompliant
            // do nothing
        }
        // PMD can not resolve the type of Integer.valueOf("2")
        if (a == Integer.valueOf("2")) { // Noncompliant
            // do nothing
        }
        if (a == new Integer("2")) {	// Noncompliant
            // do nothing
        }
    }

    private static class Inner {
        public static final Integer FLAG = 3;
    }
}


public enum OrderOperationTypeEnum {
    Locked(22, "退款订单锁定状态（有判断订单状态）"),
    Unlocked(104, "退款订单解锁状态"),
    Delete(16, "退款订单锁定状态（有判断订单状态）"),
    Locked_WithOutCheck(103, "退款订单锁定状态（不判断订单状态）"),
    ColseOrder(1,"误购取件费订单关单操作"),
    Reconciliation(38,"订单对账操作");
    private Integer code;
    private String text;
    OrderOperationTypeEnum(Integer code, String text) {
        this.code = code;
        this.text = text;
    }
    public static OrderOperationTypeEnum valueOf(Integer code) {
        for (OrderOperationTypeEnum aEnum : values()) {
            if (aEnum.code == code) { // Noncompliant
                return aEnum;
            }
        }
        return null;
    }
    public static OrderOperationTypeEnum valueOfText(String text) {
        for (OrderOperationTypeEnum aEnum : values()) {
            if (aEnum.text.equalsIgnoreCase(text)) {
                return aEnum;
            }
        }
        return null;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}