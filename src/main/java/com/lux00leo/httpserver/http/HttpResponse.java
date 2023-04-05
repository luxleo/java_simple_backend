package com.lux00leo.httpserver.http;

import com.lux00leo.httpserver.utils.Html;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class HttpResponse {
    private Logger LOGGER = LoggerFactory.getLogger(HttpResponse.class);
    private String templatePath;
    public HttpResponse(InputStream inputStream){
        handleParsedRequest(inputStream);
    }
    private void handleParsedRequest (InputStream inputStream){
        HttpParser httpParser = new HttpParser();
        HttpRequest parsedRequest = httpParser.parseHttpRequest(inputStream);
        setTemplatePath(parsedRequest.getReqTarget());
    }
    private void setTemplatePath(String reqTarget){
        if (reqTarget.equals("/")){
            this.templatePath= "src/main/resources/html/index.html";
        }else if(reqTarget.equals("/about_me") ){
            this.templatePath= "src/main/resources/html/aboutMe.html";
        }else {
            this.templatePath= "src/main/resources/html/notFound.html";
        }
    }
    public String getHTMLResponse(){
        String html = Html.writeHTML(templatePath);
        final String CRLF = "\n\r";

        String response = "HTTP/1.1 200 OK" + CRLF + // 리스폰스 헤더 작성
                "Content-Length:" + html.getBytes().length + CRLF +
                CRLF + html + CRLF + CRLF;
        return response;
    }

}
