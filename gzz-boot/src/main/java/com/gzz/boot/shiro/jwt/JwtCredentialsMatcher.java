package com.gzz.boot.shiro.jwt;

import com.gzz.core.request.Operator;
import com.gzz.core.request.OperatorContextHolder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 验码密码
 * 五次验证失败
 */
@Slf4j

public class JwtCredentialsMatcher implements CredentialsMatcher {
    private static final String RETRYLIMIT_PREFIX = "shiro:cache:retrylimit:";
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * Matcher中直接调用工具包中的verify方法即可
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        log.info("开始密码验证");
        String token = ((JwtToken)authenticationToken).getToken();
        System.out.println("authenticationToken = " + authenticationToken + ", authenticationInfo = " + authenticationInfo);

//        JwtSecretUser user = (JwtSecretUser)authenticationInfo.getPrincipals().getPrimaryPrincipal();
//        String userName = "TOKE_CODE:"+token.hashCode();
//        // 获取用户登录次数
//        AtomicInteger retryCount = (AtomicInteger) redisTemplate.opsForValue().get(getRetrylimitKey(user.getUserName()));
//        if (retryCount == null) {
//            // 如果用户没有登陆过,登陆次数加1
//            retryCount = new AtomicInteger(0);
//        }
//        if (retryCount.incrementAndGet() > JwtConstant.LOGIN_RETRY_COUNT) {
//            log.info("锁定用户" + token);
//            // 抛出用户锁定异常
//            throw new LockedAccountException();
//        }
        //得到DefaultJwtParser
        Boolean verify = JwtUtil.isVerify(token);
        log.info("JWT密码效验结果="+verify);
        if (verify) {
            // 如果正确,从缓存中将用户登录计数 清除
            // redisTemplate.delete(getRetrylimitKey(user.getUserName()));

            Claims claims = JwtUtil.parseJWT(token);

            String username = (String) claims.get("userName");
            String password = (String) claims.get("password");
            Operator operator = new Operator(username, password);
            OperatorContextHolder.setOperator(operator);
        } else {
            //redisTemplate.opsForValue().set(getRetrylimitKey(user.getUserName()), retryCount, JwtConstant.LOGIN_LOCK_TTL, TimeUnit.MINUTES);
        }
        return verify;
    }
    /**
     * 根据用户名 解锁用户
     *
     * @param username
     * @return
     */
    public void unlockAccount(String username) {
//		User user = userMapper.findByUserName(username);
//		if (user != null) {
//			// 修改数据库的状态字段为锁定
//			user.setState("0");
//			userMapper.update(user);
        redisTemplate.delete(getRetrylimitKey(username));
//		}
    }

    private String getRetrylimitKey(String username) {
        return RETRYLIMIT_PREFIX + username;
    }
}
