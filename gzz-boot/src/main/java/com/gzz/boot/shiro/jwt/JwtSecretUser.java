package com.gzz.boot.shiro.jwt;

import java.util.HashMap;

/**
 * 安全用户
 */
public class JwtSecretUser extends HashMap<String, Object> {

    public JwtSecretUser() {
    }

    public JwtSecretUser(String userName, String passwd) {
        put("userName", userName);
        put("passwd", passwd);
    }

    public String getUID() {
        return this.get("uid").toString();
    }

    public void setUID(String uid) {
        put("uid", uid);
    }

    public String getUserName() {
        return this.get("userName").toString();
    }

    public void setUserName(String userName) {
        put("userName", userName);
    }

    public String getPasswd() {
        return this.get("passwd").toString();
    }

    public void setPasswd(String passwd) {
        put("passwd", passwd);
    }
}
