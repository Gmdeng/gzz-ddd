package com.gzz.boot.shiro.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 登录过滤器
 *
 * @Shiro内置过滤器
 */
@Slf4j
public class SigninFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
                                     ServletRequest request, ServletResponse response) throws Exception {
        log.debug("登录成功");
        issueSuccessRedirect(request, response);
        //we handled the success redirect directly, prevent the chain from continuing:
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {
        log.debug("登录失败");
        String errMsg = ""; //

        if (e instanceof IncorrectCredentialsException) {
            errMsg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.";
        } else if (e instanceof ExcessiveAttemptsException) {
            errMsg = "登录失败次数过多";
        } else if (e instanceof LockedAccountException) {
            errMsg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
        } else if (e instanceof DisabledAccountException) {
            errMsg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
        } else if (e instanceof ExpiredCredentialsException) {
            errMsg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
        } else if (e instanceof UnknownAccountException) {
            errMsg = "帐号不存在. There is no user with username of " + token.getPrincipal() + e.getMessage();
        } else if (e instanceof AuthenticationException) {
            // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            errMsg = "对用户[" + token.getPrincipal() + "]进行登录验证..验证未通过,堆栈轨迹如下" + e.getMessage();
        }
        log.debug(errMsg);
        setFailureAttribute(request, e);
        return true;
    }
}
