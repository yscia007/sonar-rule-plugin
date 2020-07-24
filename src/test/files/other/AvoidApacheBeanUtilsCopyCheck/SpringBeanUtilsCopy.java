import java.lang.reflect.InvocationTargetException;
import org.springframework.beans.BeanUtils;
public class MyTest {
    public void test(Object a, Object b) throws IllegalAccessException, InvocationTargetException {
        BeanUtils.copyProperties(a, b); //Compliant
    }
}