package tv.mixiong.saas.school.config.shiro;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import tv.mixiong.saas.school.dao.CacheSessionDao;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@Order(1)
public class ShiroConfiguration {
    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

//        //自定义拦截器
//        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
//        //限制同一帐号同时在线的个数。
////        filtersMap.put("kickout", kickoutSessionControlFilter());
//        // 自定义 未授权 弹出 401 错误
//        filtersMap.put("authc", new AuthenticationFilter());
//
//        shiroFilterFactoryBean.setFilters(filtersMap);


        // 拦截器.
        //rest：比如/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user：method] ,
        // 其中method为post，getByProgramId，delete等。
        //port：比如/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal：//serverName：8081?queryString,
        // 其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。
        //perms：比如/admins/user/**=perms[user：add：*],
        // perms参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，比如/admins/user/**=perms["user：add：*,
        // user：modify：*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
        //roles：比如/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，比如/admins/user/**=roles["admin,
        // guest"],每个参数通过才算通过，相当于hasAllRoles()方法。//要实现or的效果看http://zgzty
        // .blog.163.com/blog/static/83831226201302983358670/
        //anon：比如/admins/**=anon 没有参数，表示可以匿名使用。
        //authc：比如/admins/user/**=authc表示需要认证才能使用，没有参数
        //authcBasic：比如/admins/user/**=authcBasic没有参数表示httpBasic认证
        //ssl：比如/admins/user/**=ssl没有参数，表示安全的url请求，协议为https
        //user：比如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了

        filterChainDefinitionMap.put("/cms/v1/manager/logout", "logout");
        //配置记住我或认证通过可以访问的地址
        filterChainDefinitionMap.put("/order", "roles[admin]");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/actuator", "anon");
        filterChainDefinitionMap.put("/actuator/**", "anon");

        // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;

        //这段是配合 actuator框架使用的，配置相应的角色才能访问
//        filterChainDefinitionMap.put("/health", "roles[aix]");//服务器健康状况页面
//        filterChainDefinitionMap.put("/info", "roles[aix]");//服务器信息页面
//        filterChainDefinitionMap.put("/env", "roles[aix]");//应用程序的环境变量
//        filterChainDefinitionMap.put("/metrics", "roles[aix]");
//        filterChainDefinitionMap.put("/configprops", "roles[aix]");
        filterChainDefinitionMap.put("/**/manager/login**", "anon");
        filterChainDefinitionMap.put("/**/activity/qr/**", "anon");
        filterChainDefinitionMap.put("/**/common/**", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/manager/auth/login", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/**", "authc");

        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/manager/order");
        shiroFilterFactoryBean.setLoginUrl("/manager/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(@Qualifier("shiroRealm") ShiroRealm shiroRealm, @Qualifier
            ("shiroCacheManger") CacheManager cacheManager, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(shiroRealm);
        securityManager.setSessionManager(sessionManager);
        //注入缓存管理器;
        //注意:开发时请先关闭，如不关闭热启动会报错
        securityManager.setCacheManager(cacheManager);//这个如果执行多次，也是同样的一个对象;
        //注入记住我管理器;
        return securityManager;
    }

    @Bean(name = "shiroCacheManger")
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    /**
     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
     */
    @Bean(name = "shiroRealm")
    public ShiroRealm myShiroRealm() {
        ShiroRealm myShiroRealm = new ShiroRealm();
        return myShiroRealm;
    }

    /**
     * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new
                AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public SessionManager sessionManager(SessionDAO sessionDao) {
        SessionManager sessionManager = new SessionManager();
        sessionManager.setGlobalSessionTimeout(30 * 24 * 60 * 60 * 1000L);
        sessionManager.setSessionDAO(sessionDao);
        sessionManager.setDeleteInvalidSessions(true);
        return sessionManager;
    }


}
