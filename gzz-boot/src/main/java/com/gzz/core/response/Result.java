package com.gzz.core.response;

/**
 * http 请求响应统一格式
 */
public class Result {
    private Integer code;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(ResultCode resultCode, Object data) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }

    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setCode(resultCode.code());
        result.setMessage(resultCode.message());
        return result;
    }

    public static Result failure(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setCode(resultCode.code());
        result.setMessage(resultCode.message());
        result.setData(data);
        return result;
    }

    public static Result success() {
        Result result = new Result();
        //result.set
        result.setCode(ResultCode.SUCCESS.code());
        result.setMessage(ResultCode.SUCCESS.message());
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        //result.set
        result.setCode(ResultCode.SUCCESS.code());
        result.setMessage(ResultCode.SUCCESS.message());
        result.setData(data);
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
