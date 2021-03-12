package com.gzz.boot.shiro.jwt;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JWT工具类
 */
public class JwtUtil {


    /**
     * 生成TOKEN
     * 使用Hs256算法  私匙使用用户密码
     *
     * @param secretUser 登录成功的user对象
     * @return
     */
    public static String createToken(JwtSecretUser secretUser) {
        //生成JWT的时间
        Date now = new Date();

        //下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要放到前面来。否则下面声明就无效了。
                .setClaims(secretUser)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                //iss(Issuser)：代表这个JWT的签发主体；
                .setIssuer(JwtConstant.JWT_ISSUER)
                //iat(Issued at)：是一个时间戳，代表这个JWT的签发时间；
                .setIssuedAt(now)
                // nbf(Not Before)：是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的；
                //.setNotBefore()
                //sub(Subject)：代表这个JWT的主体，即它的所有人；这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(secretUser.getUserName());
        // aud(Audience)：代表这个JWT的接收对象；
        //.setAudience("")

        // 设置过期时间
        long expMillis = now.getTime() + JwtConstant.JWT_EXPIRE_TTL;
        Date exp = new Date(expMillis);
        //exp(Expiration time)：是一个时间戳，代表这个JWT的过期时间；
        builder.setExpiration(exp);

        //设置签名使用的签名算法和签名使用的秘钥，也就是header那部分，jjwt已经将这部分内容封装好了。
        builder.signWith(SignatureAlgorithm.HS256, JwtConstant.JWT_KEY)
                .setHeaderParam("typ", "JWT");

        return builder.compact();
    }

    /**
     * 生成TOKEN
     * @param claims
     * @return
     */
    private static String createToken(Claims claims) {
        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要放到前面来。否则下面声明就无效了。
                .setClaims(claims);
        long expMillis = nowMillis + JwtConstant.JWT_EXPIRE_TTL;
        Date exp = new Date(expMillis);
        //exp(Expiration time)：是一个时间戳，代表这个JWT的过期时间；
        builder.setExpiration(exp);

        //设置签名使用的签名算法和签名使用的秘钥，也就是header那部分，jjwt已经将这部分内容封装好了。
        builder.signWith(SignatureAlgorithm.HS256, JwtConstant.JWT_KEY);

        return builder.compact();
    }

    /**
     * 刷新TOKEN
     *
     * @param token
     * @return
     */
    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(JwtConstant.JWT_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            refreshedToken = createToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 校验token
     * 在这里可以使用官方的校验，我这里校验的是token中携带的密码于数据库一致的话就校验通过
     *
     * @param token
     * @return
     */
    public static Boolean isVerify(String token) {
        try {
            parseJWT(token);
            return true;
        } catch (Exception exception) {

        }
        return false;
    }

    /**
     * Token的解密
     * 过期会抛出异常 ExpiredJwtException
     *
     * @param token 加密后的token
     * @return
     */
    public static Claims parseJWT(String token) {
        //得到DefaultJwtParser
        Claims claims = Jwts.parser()
                //设置签名的秘钥
                .setSigningKey(JwtConstant.JWT_KEY)
                //设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * token是否需要刷新TOKEN
     *
     * @return true：过期
     */
    public static boolean isExpired(Date expiration) {
        // expiration.before(new Date())
        return (expiration.getTime() - (new Date()).getTime()) < JwtConstant.NEED_REFRESH_TTL;

    }

}
