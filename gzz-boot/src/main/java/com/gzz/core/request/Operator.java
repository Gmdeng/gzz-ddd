package com.gzz.core.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 操作人员
 */
@Data
@Accessors
public class Operator implements Serializable {

    public final static String CONTEXT_KEY_USER_ID = "x-user-id";
    public final static String CONTEXT_KEY_USER_NAME = "x-user-name";
    public final static String CONTEXT_KEY_USER_TOKEN = "x-user-token";
    private String userId;
    private String userName;
    private String token;
    private String ipAddr;
    public Operator(String userId,String ipAddr ) {
        this.userId =userId;
        this.ipAddr= ipAddr;
    }
    public Operator(Map<String, String> properties) {
        this.userName = properties.get(CONTEXT_KEY_USER_NAME);
        // this.userId = Optional.ofNullable(properties.get(CONTEXT_KEY_USER_ID)).map(Long::parseLong).orElse(null);
        this.userId = properties.get(CONTEXT_KEY_USER_ID);
        this.token = properties.get(CONTEXT_KEY_USER_TOKEN);
    }

    /**
     * 将user对象转换成为http对象头
     *
     * @return http头键值对
     */
    public Map<String, String> toHttpHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTEXT_KEY_USER_ID, userId);
        headers.put(CONTEXT_KEY_USER_NAME, userName);
        headers.put(CONTEXT_KEY_USER_TOKEN, token);
        return headers;
    }
}
