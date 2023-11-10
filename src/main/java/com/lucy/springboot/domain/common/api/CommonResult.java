package com.lucy.springboot.domain.common.api;

import lombok.Data;

@Data
public class CommonResult<T> {
    /**
     * 状态码
     */
    private int code;
    /**
     * 是否成功
     */
    private Boolean isSuccess;
    /**
     * 提示信息
     */
    private String message;
    /**gi
     * 数据封装
     */
    private T data;

    protected CommonResult() {
    }

    protected CommonResult(int code, String message, T data,Boolean isSuccess) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.isSuccess = isSuccess;
    }


    /**
     * 实例化
     * @param code
     * @param message
     * @param data
     * @param isSuccess
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> instantiate(int code, String message, T data,Boolean isSuccess){
        return new CommonResult<>(code, message, data, isSuccess);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data,true);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data,true);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null,false);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode,String message) {
        return new CommonResult<T>(errorCode.getCode(), message, null,false);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null,false);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null,false);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data,false);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data,false);
    }

    public static <T> CommonResult<T> failedData(ResultCode resultCode, T data){
        return new CommonResult<>(resultCode.getCode(), resultCode.getMessage(), data, false);
    }

    public static <T> CommonResult<T> failedData(ResultCode resultCode, String message, T data){
        return new CommonResult<>(resultCode.getCode(), message, data, false);
    }
}
