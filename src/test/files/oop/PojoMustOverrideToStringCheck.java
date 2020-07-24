

import lombok.Data;

public class HeadcountOperationValidateResultVO extends BaseOperationValidateResultVO<JdOdHeadcountOperation, ODHeadCount> { // Noncompliant

}

public class FooDO extends GooDTO implements TestTestDO {         // Noncompliant
    private String tom;
    private Lond a;

    private static class NestedDTO { // Noncompliant
        private String tom;
        private boolean isBar;

        @Override
        public String tostring() {
            return tom;
        }
    }

    private static class NestedTwoDTO {
        private String tom;
        private boolean isBar;

        @Override
        public String toString() {
            return super.toString();
        }
    }

    private static class InnerClass {
        // OK
    }
}

public class GooDTO extends BackDO {  // Noncompliant
    private String tom;

    @Override
    public String toString() {
        return tom;
    }

    private static class NestedDTO {
        private String tom;
        private boolean isBar;

        @Override
        public String toString() {
            return super.toString();
        }
    }

    private static class InnerClass {
        // OK
    }
}

@Data
public class TestDO {
    private String tom;

    public class APPInfoVO implements java.io.Serializable {
        private boolean success;
    }
}


@lombok.Data
public class TestDTO {
    private String tom;
}

@Data
public class TestVO {
    private String tom;
}

@lombok.ToString
public class Test1VO {
    private String tom;
}

@ToString
public class Test1DO {
    private String tom;
}

@ToString(callSuper = true)
public class TestBO {
    private String tom;
}

public interface TestTestDO {
}

