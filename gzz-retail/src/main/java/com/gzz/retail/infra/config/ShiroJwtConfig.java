package com.gzz.retail.infra.config;

import com.gzz.boot.shiro.jwt.JwtAuthFilter;
import com.gzz.boot.shiro.jwt.JwtCredentialsMatcher;
import com.gzz.retail.infra.secret.JwtAuthorizingRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro + JWT 配置 实现无状态鉴权机制(Token)
 * 服务端不保存任何客户端请求者信息
 * 客户端的每次请求必须具备自描述信息，通过这些信息识别客户端身份
 * 客户端请求不依赖服务端的信息，多次请求不需要必须访问到同一台服务器
 * 服务端的集群和状态对客户端透明
 * 服务端可以任意的迁移和伸缩（可以方便的进行集群化部署）
 * 减小服务端存储压力
 */
@Configuration
public class ShiroJwtConfig {

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<String, Filter>();
        filterMap.put("jwt", new JwtAuthFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        //拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断

        //swagger接口\ druid 权限 开放
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        // 测试开放
        filterChainDefinitionMap.put("/auth/**", "anon");
        filterChainDefinitionMap.put("/account/**", "anon");
        filterChainDefinitionMap.put("/servlet/**", "anon");
        filterChainDefinitionMap.put("/demo/**", "anon");
        // 业务开放权限
        filterChainDefinitionMap.put("/admin/**", "anon");

        // 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        filterChainDefinitionMap.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 禁用session, 不保存用户登录状态。保证每次请求都重新认证。
     * 需要注意的是，如果用户代码里调用Subject.getSession()还是可以用session
     */
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }


    @Bean("securityManager")
    public SecurityManager securityManager(JwtAuthorizingRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 密码验证器+ 重试限制
     *
     * @return
     */
    @Bean
    public JwtCredentialsMatcher jwtCredentialsMatcher() {
        JwtCredentialsMatcher jwtCredentialsMatcher = new JwtCredentialsMatcher();
        return jwtCredentialsMatcher;
    }

    /**
     * 创建自定义的UserRealm @bean
     */
    @Bean("userRealm")
    public JwtAuthorizingRealm shiroRealm(JwtCredentialsMatcher jwtCredentialsMatcher) {
        JwtAuthorizingRealm shiroRealm = new JwtAuthorizingRealm();
        shiroRealm.setCredentialsMatcher(jwtCredentialsMatcher);
        return shiroRealm;
    }

    /**
     * 自动创建代理，没有这个鉴权可能会出错
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
