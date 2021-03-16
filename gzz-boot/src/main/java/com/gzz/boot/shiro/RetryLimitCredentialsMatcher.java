package com.gzz.boot.shiro;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 密码加密
 * 登录错误次数限制
 */
public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher {

    private static final String RETRYLIMIT_PREFIX = "shiro:cache:retrylimit:";
    private final Logger logger = LogManager.getLogger();
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        // 获取用户名
        String username = (String) token.getPrincipal();
        // 获取用户登录次数
        AtomicInteger retryCount = (AtomicInteger) redisTemplate.opsForValue().get(getRetrylimitKey(username));
        if (retryCount == null) {
            // 如果用户没有登陆过,登陆次数加1 并放入缓存
            retryCount = new AtomicInteger(0);
        }
        if (retryCount.incrementAndGet() > 5) {
            // 如果用户登陆失败次数大于5次 抛出锁定用户异常 并修改数据库字段
//			User user = userMapper.findByUserName(username);
//			if (user != null && "0".equals(user.getState())) {
//				// 数据库字段 默认为 0 就是正常状态 所以 要改为1
//				// 修改数据库的状态字段为锁定
//				user.setState("1");
//				userMapper.update(user);
//			}
            logger.info("锁定用户" + username);
            // 抛出用户锁定异常
            throw new LockedAccountException();
        }
        // 判断用户账号和密码是否正确
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            // 如果正确,从缓存中将用户登录计数 清除
            redisTemplate.delete(getRetrylimitKey(username));
        } else {
            redisTemplate.opsForValue().set(getRetrylimitKey(username), retryCount);
        }
        return matches;
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
