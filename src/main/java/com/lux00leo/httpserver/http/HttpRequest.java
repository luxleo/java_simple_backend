package com.lux00leo.httpserver.http;

import lombok.Getter;

@Getter
public class HttpRequest {
    private HttpMethod method;
    private String reqTarget;

//    public HttpRequest(){
//
//    }
    void setMethod (String parsedMethod) throws HttpException {
        for (HttpMethod method : HttpMethod.values()){
            if (parsedMethod.equals(method.name())){
                this.method = method;
                return;
            }
        }
        throw new HttpException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
    }
    void setReqTarget (String reqTarget) throws HttpException {
        if (reqTarget == null || reqTarget.length() ==0){
            throw new HttpException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        }
        this.reqTarget = reqTarget;
    }

}
