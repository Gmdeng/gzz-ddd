package com.gzz.boot.shiro;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义
 */
public class CustomAuthorizingRealm extends AuthorizingRealm {
    private final Logger logger = LogManager.getLogger();

    /**
     * 角色、权限认证
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("角色、权限认证");
        String username = (String) principals.getPrimaryPrincipal();
        // String userName = (String) principals.fromRealm(getName()).iterator().next();

        Set<String> perms = new HashSet<String>();
        Set<String> roles = new HashSet<String>();
        roles.add("supperman"); //角色
        perms.add("admin:*");// 用户的创建权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(roles);
        authorizationInfo.addStringPermissions(perms);
        return authorizationInfo;
    }

    /**
     * 身份认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("身份认证");
        String username = (String) token.getPrincipal();

        if (username != null && "admin".equalsIgnoreCase(username) == false) {
            throw new UnknownAccountException("没有找到该账号.....");
        }
        logger.debug("登录验证密码");
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, "4ee9b3e0428aa75a6317e5a20cfd9667", getName());
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("htest"));
        return authenticationInfo;
    }


    /**
     * 当前用户授权信息缓存.
     */
    public void clearCached(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        super.clearCachedAuthorizationInfo(principals);
        super.clearCachedAuthenticationInfo(principals);
    }

}
