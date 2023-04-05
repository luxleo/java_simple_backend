package com.lux00leo.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread{
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);
    private int port;
    private String webroot;
    private ServerSocket serverSocket;

    public ServerListenerThread(int port, String webroot) throws IOException {
        // 한스레드에서 하나의 큐로 처리 하기 때문에 여러개의 연결이 올 경우 응답이 지연 되면 큐 내부의 다른 요청은 처리 받지 못한다.
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {

        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                LOGGER.info(" * Connection accepted: " + socket.getInetAddress());

                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();

            }
            //serverSocket.close();
        } catch (IOException e) {
            LOGGER.error("소켓 설정에 문제 발생",e);
        } finally {
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
