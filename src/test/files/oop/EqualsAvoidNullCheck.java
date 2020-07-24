
public class Foo {
    private String alias;
    private String parameterName;
    private static final String TYPE_ORACLE = "";
    private static final String TYPE_SQLSERVER = "";

//    public void getConnection(String url,String user,String pwd,String type)throws Exception {
//        if (type.equals(TYPE_ORACLE)) { // Noncompliant
//
//        } else if(type.equals(TYPE_SQLSERVER)){ // Noncompliant
//
//        }
//    }

    protected boolean validateDataAuth(JopRequestContext requestContext, AppInfoDto appInfoDto) throws Exception {
        boolean flag = true;
        String method = requestContext.getMethod();
        String defaultSystemResCode = "___EMPTY-NULL___";//默认绑定的系统资源码
        String systemResCode = defaultSystemResCode;
        AppDto app = ListUtils.first(authCachePullDao.pullApps(appInfoDto.getBoundAppId()));
        String boundSystemResCode = "";
        if (app != null) {
            boundSystemResCode = app.getResCodeStr();
        }
        if (AUTHORIZATION_MENU_API.containsKey(method)) {//获取菜单数据
            systemResCode = ((SystemMenuRequest) requestContext.getJopRequest()).getSystemResCode();
        } else if (method.equals("uim.auth.res.getMenuFuns") ||
                method.equals("uim.auth.res.getAuthMenuFuns")) {

        }
    }

    public static boolean isBooleanValue(String data) {
        String trimmed = data.trim().toLowerCase();
        return trimmed.equals("true") ||  // Noncompliant
                trimmed.equals("t"); // Noncompliant
    }

    class Role {
        private String roleName;

        public String getRoleName() {
            return this.roleName;
        }
    }
    Role role = new Role();
    public void bar() {
        String applicationName;
        String emptyOptionTitle;

        if ("kk".equals("tom")) {
            return;
        }

        if (role.getRoleName().equals("超级管理员")) {  // Noncompliant

        }
        if (!role.getRoleName().equals("超级管理员")) {   // Noncompliant

        }

        String name;
        if (name != null && name.equals("tom")) {
            if (null != parameterName && !parameterName.equals("")) {
                return;
            }
        }

        if(null!=applicationName && !applicationName.equals("")) {

        }

        if (emptyOptionTitle==null || emptyOptionTitle.trim().equals("")){
            return;
        }

        if (name.toString().equals("tom")) { // Noncompliant
            return;
        }

        if (this.alias.equals("tom")) { // Noncompliant
            return;
        }

        Integer a;
        if (a.equals(3)) {
            return;
        }
    }
}

public class Moo {
    private static final String TOM = "tom";

    public void bar() {
        String label;
        "abc".equals(label);
        "abc".equals(label.toString());
        if (label.equals(TOM)) {		// Noncompliant
            return;
        }
    }
}

public class Aoo {
    public void bar() {
        String name;
        String label;
        if (name.equals(label)) {
            return;
        }
    }
}

public class Xoo {
    public static class Inner1 {
        public void bar() {
            String name;
            if (name.equals("")) {     // Noncompliant
                return;
            }
        }
    }
    public static class Inner2 {
        public void bar() {
            String name;
            if (name.equals("")) {     // Noncompliant
                return;
            }
        }
    }
}

public class Coo {
    private static final ThreadLocal<Boolean> LOCAL_TEST_FLAG = new ThreadLocal<Boolean>();

    public static boolean isLoadTestFlag() {
        return Boolean.TRUE.equals(LOCAL_TEST_FLAG.get());
    }
}


public class PSDBUtil {
    public static final String TYPE_ORACLE = "oracle";
    public static final String TYPE_SQLSERVER = "sqlserver";

    private Logger LOG = LoggerFactory.getLogger(DBUtil.class);

    public static boolean isBooleanValue(String data) {
        String sex = psPerson.getSex();
        if(!VelocityTools.strIsEmpty(sex)) {
            sex = sex.toUpperCase();
            if (sex.equals("M")) { // Noncompliant
            }
        }

        String trimmed = data.trim().toLowerCase();
        return trimmed.equals("true") ||  // Noncompliant
                trimmed.equals("t"); // Noncompliant
    }

    public Connection getConnection(String url, String user, String pwd, String type) throws Exception {
        if (type.equals(TYPE_ORACLE)) {  // Noncompliant
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } else if (type.equals(TYPE_SQLSERVER)) {  // Noncompliant
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } else {
            throw new Exception("获取数据库连接失败,数据库类型错误");
        }

        return DriverManager.getConnection(url, user, pwd);
    }
}
