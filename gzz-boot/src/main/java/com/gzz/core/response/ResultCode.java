package com.gzz.core.response;

public enum ResultCode {
    /*成功状态码*/
    SUCCESS(1, "成功"),
    /*参数错误 状态码 1001 - 1999 */
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数不能为空"),
    PARAM_TYPE_BIND_ERROR(10003, "参数不能为空"),
    PARAM_NOT_COMPLETE(10004, "参数不能为空")
    /*参数错误 状态码 2001 - 2999 */;


    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }


}
