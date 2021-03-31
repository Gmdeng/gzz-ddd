package com.gzz.core.response;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * http 请求结果响应统一格式
 */
public class HttpResult extends HashMap<String, Object> {
    /**
     * 构造函数
     */
    public HttpResult() {
        put("code", 0);
        put("msg", "成功");
        put("timestamp", LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli()); // 毫秒数时间戳（13位）
    }

    /**
     * 默认成功响应
     * @return
     */
    public static HttpResult success() {
        return new HttpResult();
    }

    /**
     * 成功响应, 自定义信息
     * @param msg 自定义成功信息
     * @return
     */
    public static HttpResult success(String msg) {
        return result(0, msg);
    }

    /**
     * 默认成功响应, 另外返回数据。
     * @param data
     * @return
     */
    public static HttpResult success(Object data) {
        return result(0, "成功", data);
    }

    /**
     * 默认失败响应
     * @return
     */
    public static HttpResult fail() {
        return result(999, "失败");
    }

    /**
     * 失败响应, 自定义信息
     * @param msg 自定义失败信息
     * @return
     */
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
    /**
     * 自定义应响
     * @param map
     * @return
     */
    public static HttpResult result(Map<String, Object> map) {
        HttpResult httpResult = new HttpResult();
        httpResult.putAll(map);
        return httpResult;
    }
    /**
     * 自定义应响
     * @param code
     * @param msg
     * @return
     */
    public static HttpResult result(int code, String msg) {
        HttpResult httpResult = new HttpResult();
        httpResult.put("code", code);
        httpResult.put("msg", msg);
        return httpResult;
    }

    /**
     * 自定义应响
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static HttpResult result(int code, String msg, Object data) {
        HttpResult httpResult = new HttpResult();
        httpResult.put("code", code);
        httpResult.put("msg", msg);
        httpResult.put("data", data);
        return httpResult;
    }

    /**
     * 设置数据包
     * @param data
     * @return
     */
    public HttpResult data(Object data) {
        super.put("data", data);
        return this;
    }

    /**
     * 设置信息
     * @param msg
     * @return
     */
    public HttpResult message(String msg) {
        super.put("msg", msg);
        return this;
    }

    /**
     * 自定义设置
     * @param key
     * @param val
     * @return
     */
    public HttpResult put(String key, Object val) {
        super.put(key, val);
        return this;
    }

    /**
     * 设置签名串
     * @param signStr
     * @return
     */
    public HttpResult sign(String signStr){
        super.put("sign", signStr);
        return this;
    }
}
