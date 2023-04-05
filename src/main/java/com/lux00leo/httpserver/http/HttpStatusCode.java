package com.lux00leo.httpserver.http;

public enum HttpStatusCode {
    // client error
    CLIENT_ERROR_400_BAD_REQUEST(400,"Bad request"),
    // server error
    SERVER_ERROR_501_NOT_IMPLEMENTED(501, "Not Implemented"),
    SERVER_ERROR_500_INTERNAL_SERVER_ERROR(500,"Internal server error");
    public final int STATUS_CODE;
    public final String MESSAGE;

    HttpStatusCode (int STATUS_CODE,String MESSAGE){
        this.STATUS_CODE = STATUS_CODE;
        this.MESSAGE = MESSAGE;
    }
}
