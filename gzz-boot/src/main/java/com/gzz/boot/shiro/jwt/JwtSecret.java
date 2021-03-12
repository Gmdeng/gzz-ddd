package com.gzz.boot.shiro.jwt;

/**
 * 主要常量
 */
public class JwtSecret {
   //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，
   // 切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
   public static final String JWTKey ="JO6HN3NGIU25G2FIG8V7VD6CK9B6T2Z5";
   // 有效时间(50分钟)
   public static final long JWTExpire = 50 * 60 * 1000;
   // 需要刷新TOKEN(5分钟)
   public static final long NEED_REFRESH_TTL = 5 * 60 * 1000;
   // 签发单位
   public static final String JWTIssuer ="G-ZZ.studio";
}
