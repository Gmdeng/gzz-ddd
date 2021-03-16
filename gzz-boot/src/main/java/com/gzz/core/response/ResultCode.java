package com.gzz.core.response;

/**
 * 响应代码
 */
public enum ResultCode {
    /*成功状态码*/
    SUCCESS(1, "成功"),
    /* 参数错误 代码 1000 - 1999 */
    PARAM_ERROR(1000, "参数异常"),
    PARAM_IS_INVALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数不能为空"),
    PARAM_TYPE_BIND_ERROR(1003, "参数不能为空"),
    PARAM_NOT_COMPLETE(1004, "参数不能为空"),
    /* 业务错误 状态码 2000 - 2999 */
    BIZZ_ERROR(2000, "业务异常 "),
    /* 授权权限错误 状态码 4000 - 4999 */
    AUTH_ERROR(4000, "权限"),
    /* 数据库错误 状态码 8000 - 8999 */
    DATABASE_ERROR(8000, "FEGwgkd ");
    private int code;
    private String text;

    ResultCode(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int code() {
        return this.code;
    }

    public String text() {
        return this.text;
    }


}
