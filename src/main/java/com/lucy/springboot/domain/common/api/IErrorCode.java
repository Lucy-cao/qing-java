package com.lucy.springboot.domain.common.api;

/**
 * 常用API返回对象接口
 *   2019/4/19.
 */
public interface IErrorCode {
    /**
     * 返回码
     */
    int getCode();

    /**
     * 返回信息
     */
    String getMessage();
}

