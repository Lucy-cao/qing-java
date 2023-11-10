package com.lucy.springboot.domain.common.exception;

import com.lucy.springboot.domain.common.api.CommonResult;
import com.lucy.springboot.domain.common.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理
 *   2020/2/27.
 */
//@ControllerAdvice
//@ResponseBody
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获通用异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult allHandler(Exception e) {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes != null){
//            HttpServletRequest request = attributes.getRequest();
//            log.error("tenant({}) error url: {}", getTenantInfoByToken(request.getHeader("Authorization")), request.getRequestURI());
            log.error(e.getMessage(), e);
        }

        return CommonResult.failed("服务器内部错误");
    }

    /**
     * 捕获自定义的接口异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    /**
     * 传参验证失败异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult handleValidException(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }

    @ExceptionHandler(value = BindException.class)
    public CommonResult handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }

    /**
     * 处理 @RequestParam @PathVariable 参数验证失败
     *
     * @param e
     * @return
     * @Author zrr
     * @Date 2022/4/24 15:08
     * @throws
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonResult handlerConstraintViolationException(ConstraintViolationException e) {
        return CommonResult.validateFailed(e.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse(e.getMessage()));
    }

    /**
     * 处理 @RequestParam 参数验证失败（整个不传）
     *
     * @param e
     * @return
     * @Author zrr
     * @Date 2022/4/24 15:08
     * @throws
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public CommonResult handlerMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return CommonResult.validateFailed(e.getMessage());
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public CommonResult handlerMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return CommonResult.validateFailed("文件大小超出异常");
    }

    /**
     * 处理 @RequestParam 参数类型有误
     *
     * @param e
     * @return
     * @Author xjq
     * @Date
     * @throws
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public CommonResult handlerHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        LogUtil.debug(e);
        return CommonResult.validateFailed("查询条件有误");
    }
}
