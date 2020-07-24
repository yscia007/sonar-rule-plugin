package com.jd.sonar.java.itqa.test.concurrent;

import com.jd.ssa.domain.UserInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class ThreadLocalTest {
    private String test;
    private ThreadLocal<String> local;
    private static ThreadLocal<String> local2; // Noncompliant
    private static ThreadLocal<ClusterJmsTemplate> mqProducerConfigCache = new ThreadLocal<ClusterJmsTemplate>();// Noncompliant
    private static final ThreadLocal<DateFormat> df1 = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
    private static final ThreadLocal<DateFormat> df2 = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    public void init() {
        test = test.toUpperCase();
    }
    public void remove(){
        local.remove();
        //local2.remove();
        test.length();
    }
}



/**
 * @author lushun5 2017/12/20
 */
@Service
@Transactional
public class UimAuthApiImpl implements UimAuthApi {

    private Map<String, Object> getResourceCodeFromCache(String userName) {
        if (this.cache == null) {
            return null;
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("UIM本地缓存大小:{}", this.cache.size());
            }
            Map<String, Object> resourceCodeMap = this.cache.get(userName);
            if (resourceCodeMap != null) {
                long expire = ((Long) resourceCodeMap.get("expire")).longValue();
                if (System.currentTimeMillis() - expire > 0) {
                    this.cache.remove(userName);
                    return null;
                } else {
                    return resourceCodeMap;
                }
            }
            return null;
        }
    }
}


/**
 * 在一个请求的生命周期可用，在拦截器注入，
 *
 * @author <a href="mailto:wanghaiinfo@jd.com">wanghai</a>
 */
public class ServletContext {
    private HttpServletResponse response;
    private ApplicationContext applicationContext;
    private HttpServletRequest request;
    private CookieLocaleResolver localeResolver;

    private static final ThreadLocal<ServletContext> holder = new ThreadLocal<ServletContext>();
    public static final String USER = "servlet.user";
    public static final String IP = "servlet.request.remoteip";

    private ServletContext() {
    }
    public static final ServletContext getServletContext() {
        ServletContext context = holder.get();
        return context;
    }
    public final HttpServletRequest getRequest() {
        return this.request;
    }
    public final HttpServletResponse getResponse() {
        return this.response;
    }
    public static final ApplicationContext getApplicationContext() {
        ServletContext context = getServletContext();
        if (context == null) {
            return null;
        }
        return context.applicationContext;
    }
    public final String getContextPath() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return (String) request.getContextPath();
    }
    public final String getRequestURI() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return (String) request.getRequestURI();
    }
    public final Locale getLocale() {
        ServletContext context = getServletContext();
        if (context == null) {
            return null;
        }
        Locale locale = context.localeResolver.resolveLocale(context.request);
        return locale;
    }
    public final String getIP() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        String ip = null;
        try {
            ip = (String) request.getAttribute(IP);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ip;
    }
    public final UserInfo getUser() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return (UserInfo) request.getAttribute(USER);
    }
    public static final void remove() {
        holder.remove();
    }
    public static final void initContext(
            HttpServletRequest request,
            HttpServletResponse response,
            ApplicationContext applicationContext) {
        ServletContext context = new ServletContext();
        context.request = request;
        context.response = response;
        context.applicationContext = applicationContext;
        holder.set(context);
    }
    public void set(
            String key,
            Object value) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.setAttribute(key, value);
        }
    }

}

