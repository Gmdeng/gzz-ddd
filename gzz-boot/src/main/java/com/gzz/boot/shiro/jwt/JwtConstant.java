package com.gzz.boot.shiro.jwt;

/**
 *
 */
public class JwtConstant {
    //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，
    // 切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
    public static final String JWT_KEY = "JO6HN3NGIU25G2FIG8V7VD6CK9B6T2Z5";
    // 有效时间(单位毫秒) 24小时
    public static final long JWT_EXPIRE_TTL = 24 * 60 * 60 * 1000;
    // 签发单位
    public static final String JWT_ISSUER = "G-zz@studio";
    // 需要刷新TOKEN(单位毫秒) (5分钟)
    public static final long NEED_REFRESH_TTL = 60 * 60 * 1000;
    // 登录可偿试次数
    public static final int LOGIN_RETRY_COUNT = 10;
    // 连续登录偿试失败后锁定时间（单位分钟）
    public static final int LOGIN_LOCK_TTL = 60;
}
