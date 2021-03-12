package com.gzz.boot.shiro.filter;

import com.gzz.boot.shiro.CustomAuthorizingRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 退出系统 过滤器
 */
@Slf4j
public class SignoutFilter extends LogoutFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        log.info("行退出系统前需要清空的数据...");
        // 在这里执行退出系统前需要清空的数据
        Subject subject = getSubject(request, response);
        String redirectUrl = getRedirectUrl(request, response, subject);

        // 清除缓存
        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        CustomAuthorizingRealm authorizingRealm = (CustomAuthorizingRealm) securityManager.getRealms().iterator().next();
        if (subject.getPrincipal() != null)
            authorizingRealm.clearCached(subject.getPrincipal().toString());
        try {
            subject.logout();
        } catch (SessionException ise) {
            ise.printStackTrace();
        }
        issueRedirect(request, response, redirectUrl);
        // 返回false表示不执行后续的过滤器，直接返回跳转到登录页面
        return Boolean.FALSE;

    }

}
