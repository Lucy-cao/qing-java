package com.lucy.springboot.domain.common.api;

public enum ResultCode implements IErrorCode{
    SUCCESS(200, "操作成功"),
    Accepted(202, "已接收请求但需要确认"),
    FAILED(500, "操作失败"),

    NO_PASS_WITH_DATA(510, "失败，请按页面提示操作"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");

    private int code;
    private String message;

    private ResultCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
