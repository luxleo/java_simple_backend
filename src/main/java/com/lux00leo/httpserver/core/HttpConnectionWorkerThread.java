package com.lux00leo.httpserver.core;

import com.lux00leo.httpserver.http.HttpResponse;
import com.lux00leo.httpserver.utils.Html;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread{
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;
    public HttpConnectionWorkerThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
             inputStream = socket.getInputStream();
             outputStream = socket.getOutputStream();

//             String html = Html.writeHTML();
//            //String html = "<html><head><title>Simple Java Http!</title></head><body><h1>good to go bro!</h1></body></html>";
//            final String CRLF = "\n\r";
//
//            String response = "HTTP/1.1 200 OK" + CRLF + // 리스폰스 헤더 작성
//                    "Content-Length:" + html.getBytes().length + CRLF +
//                    CRLF + html + CRLF + CRLF;
            HttpResponse httpResponse = new HttpResponse(inputStream);
            String response = httpResponse.getHTMLResponse();
            outputStream.write(response.getBytes());


            LOGGER.info("새로운 연결 스레드 생성 완료");
        } catch (IOException e){
            LOGGER.error("워커 스레드에 문제 발생",e);
        } finally {
            if (inputStream!= null) {
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
