package com.lux00leo.httpserver.http;

public class HttpException extends Exception{
    private final HttpStatusCode errorCode;
    public HttpException(HttpStatusCode errorCode){
        super(errorCode.MESSAGE);
        this.errorCode = errorCode;
    }
}
