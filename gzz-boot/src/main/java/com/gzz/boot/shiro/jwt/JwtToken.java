package com.gzz.boot.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * AuthenticationToken: shiro中负责把username,password生成用于验证的token的封装类
 * 我们需要自定义一个对象用来包装token。
 */
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
