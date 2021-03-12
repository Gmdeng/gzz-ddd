package com.gzz.retail.infra.secret;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.gzz.boot.shiro.jwt.JwtToken;
import com.gzz.boot.shiro.jwt.JwtUtil;
import com.gzz.boot.shiro.jwt.JwtSecretUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;
import java.util.Set;

/**
 * security
 */
@Slf4j
public class JwtAuthorizingRealm extends AuthorizingRealm {

    /**
     * 必须重写此方法，不然Shiro会报错
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     *  shiro 身份验证
     * @param token
     * @return  boolean
     * @throws AuthenticationException 抛出的异常将有统一的异常处理返回给前端
     *
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("shiro 身份验证");
        /**
         *  AuthenticationToken
         *  JwtToken重写了AuthenticationToken接口 并创建了一个接口token的变量
         *   因为在filter我们将token存入了JwtToken的token变量中
         *   所以这里直接getToken()就可以获取前端传递的token值
         */
        String JWTtoken = ((JwtToken) token).getToken();

        /**
         * TODO
         * 这里可以增加REDIS验证。。取用哈希值
         */
        /**
         *  Claims对象它最终是一个JSON格式的对象，任何值都可以添加到其中
         *  token解密  转换成Claims对象
         */
        try {
            Claims claims = JwtUtil.parseJWT(JWTtoken);
            /**
             *   根据JwtUtil加密方法加入的参数获取数据e
             *   查询数据库获得对象e
             *   如为空：抛出异常
             *   如验证失败抛出 AuthorizationException
             */
            String username = (String) claims.get("userName"); // claims.getSubject();
            String password = (String) claims.get("password");
            JwtSecretUser principal = null; // 从数据库获取
            return new SimpleAuthenticationInfo(username, password, getName());
        }catch (ExpiredJwtException e) {
            throw new ExpiredCredentialsException("TOKE过期");
        }catch(Exception ex){
            log.error(ex.getMessage());
        }
        return null;
    }

    /**
     * 角色、权限认证
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("shiro 授权验证");
        SimpleAuthorizationInfo info = null;
        /**
         *  PrincipalCollection对象
         *  文档里面描述：返回从指定的Realm 仅作为Collection 返回的单个Subject的对象，如果没有来自该领域的任何对象，则返回空的Collection。
         *  在登录接口放入权限注解返回的错误信息：Subject.login(AuthenticationToken)或SecurityManager启用'Remember Me'功能后成功自动获取这些标识主体
         *  当调用Subject.login()方法成功后 PrincipalCollection会自动获得该对象  如没有认证过或认证失败则返回空的Collection并抛出异常
         * 	getPrimaryPrincipal()：返回在应用程序范围内使用的主要对象，以唯一标识拥有帐户。
         */
        Object principal = principals.getPrimaryPrincipal();
        /**
         * 得到身份对象
         * 查询该用户的权限信息
         */
        JwtSecretUser user = (JwtSecretUser) principal;
        List<String> roleModels = Lists.newArrayList();
        try {
            /**
             * 创建一个Set,来放置用户拥有的权限
             * 创建 SimpleAuthorizationInfo, 并将办好权限列表的Set放入.
             */
            Set<String> rolesSet = Sets.newHashSet();
            for (String role : roleModels) {
                rolesSet.add(role);
            }
            info = new SimpleAuthorizationInfo();
            info.setStringPermissions(rolesSet);   // 放入权限信息
        }catch (Exception e){
            throw new AuthenticationException("授权失败!");
        }
        return info;
    }
}
