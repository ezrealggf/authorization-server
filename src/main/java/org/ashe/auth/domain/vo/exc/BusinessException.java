package org.ashe.auth.domain.vo.exc;

import org.ashe.auth.domain.vo.resp.RespCode;
import org.ashe.auth.domain.vo.resp.ResultCode;

import java.io.Serializable;

/**
 * 简单业务异常 此异常不爬取堆栈信息
 */
@SuppressWarnings("all")
public class BusinessException extends StateCodeException {

    private final transient Object data;

    private final boolean writableStackTrace;

    public BusinessException() {
        this(RespCode.FAIL);
    }

    public BusinessException(String message) {
        this(RespCode.FAIL.getCode(), message);
    }

    public BusinessException(String code, String message, Serializable data) {
        this(code, message, data, false);
    }

    public BusinessException(String code, String message) {
        this(code, message, null, false);
    }

    public BusinessException(ResultCode resultCode) {
        this(resultCode, false);
    }

    public BusinessException(ResultCode resultCode, boolean writableStackTrace) {
        this(resultCode.getCode(), resultCode.getMessage(), null, writableStackTrace);
    }

    public BusinessException(ResultCode resultCode, Serializable data) {
        this(resultCode.getCode(), resultCode.getMessage(), data, false);
    }

    public BusinessException(String code, String message, Serializable data, boolean writableStackTrace) {
        super(code, message, writableStackTrace);
        this.data = data;
        this.writableStackTrace = writableStackTrace;
    }

    public Object getData() {
        return data;
    }

    public boolean getWritableStackTrace() {
        return writableStackTrace;
    }
}