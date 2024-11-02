package com.bbs.cloud.common.error;

public class HttpException extends RuntimeException {

    protected Integer code;

    protected String message;

    public HttpException(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
