import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

public class AvoidApacheBeanUtilsCopyRule {
    public void test(Object a, Object b) throws IllegalAccessException, InvocationTargetException {
        BeanUtils.copyProperties(a, b);  // Noncompliant
    }
}