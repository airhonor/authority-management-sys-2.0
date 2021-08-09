package com.hz.oauth2.server.aspect;

import com.hz.oauth2.server.constant.ResultCode;
import com.hz.oauth2.server.exception.PermissionDeniedException;
import com.hz.oauth2.server.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @program: OAuth-2.0
 * @author: zgr
 * @create: 2021-07-01 16:48
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionTranslator {


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BaseResponse<Object> handleError(MissingServletRequestParameterException e) {
        log.warn("Missing Request Parameter", e);
        String msg = String.format("Missing Request Parameter: %s", e.getParameterName());
        return BaseResponse
                .builder()
                .code(ResultCode.PARAM_MISS.getCode())
                .msg(msg)
                .data(null)
                .build();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public BaseResponse<Object> handleError(UsernameNotFoundException e) {
        log.warn("Missing Request Parameter", e);
        String msg = String.format("Missing Request Parameter: %s", e.getMessage());
        return BaseResponse
                .builder()
                .code(ResultCode.UN_AUTHORIZED.getCode())
                .msg(msg)
                .data(null)
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseResponse<Object> handleError(MethodArgumentTypeMismatchException e) {
        log.warn("Method Argument Type Mismatch", e);
        String msg = String.format("Method Argument Type Mismatch: %s", e.getName());
        return BaseResponse
                .builder()
                .code(ResultCode.PARAM_TYPE_ERROR.getCode())
                .msg(msg)
                .data(null)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<Object> handleError(MethodArgumentNotValidException e) {
        log.warn("Method Argument Not Valid", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String msg = String.format("%s:%s", error.getField(), error.getDefaultMessage());
        return BaseResponse
                .builder()
                .code(ResultCode.PARAM_VALID_ERROR.getCode())
                .msg(msg)
                .data(null)
                .build();
    }

    @ExceptionHandler(BindException.class)
    public BaseResponse<Object> handleError(BindException e) {
        log.warn("Bind Exception", e);
        FieldError error = e.getFieldError();
        String msg = String.format("%s:%s", error.getField(), error.getDefaultMessage());
        return BaseResponse
                .builder()
                .code(ResultCode.PARAM_BIND_ERROR.getCode())
                .msg(msg)
                .data(null)
                .build();
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResponse<Object> handleError(NoHandlerFoundException e) {
        log.error("404 Not Found", e);
        return BaseResponse
                .builder()
                .code(ResultCode.NOT_FOUND.getCode())
                .msg(e.getMessage())
                .data(null)
                .build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse<Object> handleError(HttpRequestMethodNotSupportedException e) {
        log.error("Request Method Not Supported", e);
        return BaseResponse
                .builder()
                .code(ResultCode.METHOD_NOT_SUPPORTED.getCode())
                .msg(e.getMessage())
                .data(null)
                .build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public BaseResponse<Object> handleError(HttpMediaTypeNotSupportedException e) {
        log.error("Media Type Not Supported", e);
        return BaseResponse
                .builder()
                .code(ResultCode.MEDIA_TYPE_NOT_SUPPORTED.getCode())
                .msg(e.getMessage())
                .data(null)
                .build();
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public BaseResponse<Object> handleError(PermissionDeniedException e) {
        log.error("Permission Denied", e);
        return BaseResponse
                .builder()
                .code(e.getCode())
                .msg(e.getMessage())
                .data(null)
                .build();
    }

//    @ExceptionHandler(InsufficientAuthenticationException.class)
//    public BaseResponse<Object> handleError(InsufficientAuthenticationException e) {
//        log.error("Permission Denied", e);
//        return BaseResponse
//                .builder()
//                .code(ResultCode.INTERNAL_SERVER_ERROR.getCode())
//                .msg(e.getMessage())
//                .data(null)
//                .build();
//    }

//    @ExceptionHandler(Throwable.class)
//    public BaseResponse<Object> handleError(Throwable e) {
//        log.error("Internal Server Error", e);
//        return BaseResponse
//                .builder()
//                .code(ResultCode.INTERNAL_SERVER_ERROR.getCode())
//                .msg(e.getMessage())
//                .data(null)
//                .build();
//    }


}
