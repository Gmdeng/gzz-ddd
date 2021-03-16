package com.gzz.core.response;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * http 请求响应统一格式
 */
public class HttpResult extends HashMap<String, Object> {

    public HttpResult() {
        put("code", 0);
        put("msg", "成功");
        put("timestamp", LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli()); // 毫秒数时间戳（13位）
    }

    public static HttpResult success() {
        return new HttpResult();
    }

    public static HttpResult success(String msg) {
        return result(0, msg);
    }

    public static HttpResult success(Object data) {
        return result(0, "成功", data);
    }

    public static HttpResult fail() {
        return result(999, "失败");
    }


    public static HttpResult fail(String msg) {
        return result(999, msg);
    }

    public static HttpResult fail(ResultCode ret) {
        return result(ret.code(), ret.text());
    }

    public static HttpResult warn() {
        return result(666, "失败");
    }

    public static HttpResult warn(String msg) {
        return result(666, msg);
    }

    public static HttpResult result(Map<String, Object> map) {
        HttpResult httpResult = new HttpResult();
        httpResult.putAll(map);
        return httpResult;
    }

    public static HttpResult result(int code, String msg) {
        HttpResult httpResult = new HttpResult();
        httpResult.put("code", code);
        httpResult.put("msg", msg);
        return httpResult;
    }

    public static HttpResult result(int code, String msg, Object data) {
        HttpResult httpResult = new HttpResult();
        httpResult.put("code", code);
        httpResult.put("msg", msg);
        httpResult.put("data", data);
        return httpResult;
    }

    public HttpResult data(Object data) {
        super.put("data", data);
        return this;
    }

    public HttpResult message(String msg) {
        super.put("msg", msg);
        return this;
    }

    public HttpResult put(String key, Object val) {
        super.put(key, val);
        return this;
    }

}
