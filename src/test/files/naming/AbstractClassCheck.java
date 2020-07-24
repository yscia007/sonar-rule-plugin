
import java.lang.Object;
import java.io.Serializable;

class ClasName {
    void foo() {
        new Object() {};
    }
}

enum Enum {
}

class AbstractLikeName {
}

abstract public class BadAbstractClassName { // Noncompliant
}

abstract class AbstractClassName {
}

abstract class BaseClassName {
}

abstract class BasClassName {  // Noncompliant
}

public class BaseResultVo implements Serializable {

}

public class BaseUserInfo {

}