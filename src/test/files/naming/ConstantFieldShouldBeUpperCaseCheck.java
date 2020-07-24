
import lombok.extern.java.Log;

import java.util.logging.Logger;

//import com.sun.tools.javac.util.Log;

public class ConstantFieldNameRuleTest {
    private  static final int MAX = 5;
    private  static final long serialVersionUID = 1L;
    //except Log
    private static final Logger logger = new Logger();
    private static final Log log = new Log();
    //Constant variable names should be written in upper characters separated by underscores
    private static final boolean success = true;  // Noncompliant
    private static final Integer xxxService = 1;  // Noncompliant
    private static final Integer xxxxservice = 1;  // Noncompliant
    private String testString;
    public void f() {
        int il = 0;
    }

    private class  InnerClass {
        private static final String testStr = "abc"; // Noncompliant
    }
}

@Api(value = "/jcloud/upload", description = "文件上传接口服务")
@Controller
@RequestMapping("/jcloud/upload")
public class UploadController {
    private String bucket = JcloudConstant.JCLOUDBUCKET;
    private static final Logger logger = Logger.getLogger(UploadController.class);
    private static final Set<String> limitFile = new HashSet<String>() {{  // Noncompliant
        add("js");
        add("exe");
        add("sh");
        add("bat");
        add("html");
        add("htm");
        add("swf");
        add("jsp");
        add("php");
        add("py");
        add("");
    }};

    private static final com.google.protobuf.Internal.EnumLiteMap<RecordState> internalValueMap =  // Noncompliant
            new com.google.protobuf.Internal.EnumLiteMap<RecordState>() {
                public RecordState findValueByNumber(int number) {
                    return RecordState.forNumber(number);
                }
            };
}