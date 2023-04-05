package com.lux00leo.httpserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    // request line token
    private final static int SP = 0x20;
    private final static int CR = 0x0D;
    private final static int LF = 0x0A;

    public HttpRequest parseHttpRequest(InputStream inputStream){
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        HttpRequest req = new HttpRequest();
        try {
            parseReqLine(reader,req);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        }
        return req;
    }
    private void parseReqLine(InputStreamReader reader, HttpRequest req) throws IOException, HttpException {
        StringBuilder sb = new StringBuilder();
        boolean isMethodParsed = false;
        boolean isReqTargetParsed = false;

        int _byte;
        while ((_byte=reader.read()) >=0){
            if (_byte == CR){
                _byte = reader.read();
                if(_byte == LF){
                    if(!isMethodParsed || !isReqTargetParsed){
                        throw new HttpException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    return;
                }else{
                    throw new HttpException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
            } else if (_byte == SP) {
                if (!isMethodParsed){
                    req.setMethod(sb.toString());
                    isMethodParsed=true;
                } else if (!isReqTargetParsed) {
                    req.setReqTarget(sb.toString());
                    isReqTargetParsed = true;
                }else {
                    throw new HttpException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
                // 띄어쓰기 후 버퍼 내용 비워서 초기화( method,request target,version으로 토큰화 해서 보기 때문)
                sb.delete(0,sb.length());
            }else {
                sb.append((char)_byte);
            }
        }
    }
}
