package com.gzz.core.exception;

/**
 * 运行时业务异常
 */

public class BizzException extends RuntimeException {

    /**
     * 错误编码
     */
    protected Integer code;

    /**
     * 描述用于展示
     */
    protected String desc;

    /**
     * 错误消息
     */
    protected String message;

    public BizzException(Integer code, String message, String desc, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.message = message;
        this.desc = desc;
    }

    public BizzException(Integer code, String message, String desc) {
        super(message);
        this.code = code;
        this.message = message;
        this.desc = desc;
    }

    public BizzException() {
        this(99997, "内部错误", "内部错误");
    }

    public BizzException(Throwable throwable) {
        this(99997, "内部错误", "内部错误", throwable);
    }

    public BizzException(String message) {
        this(99997, message, "内部错误");
    }


    public BizzException(Integer code, String message, Throwable throwable) {
        this(code, message, message, throwable);
    }

    public BizzException(Integer code, String message) {
        this(code, message, message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
