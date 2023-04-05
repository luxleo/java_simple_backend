package com.lux00leo.httpserver;

import com.lux00leo.httpserver.config.Configuration;
import com.lux00leo.httpserver.config.ConfigurationManager;
import com.lux00leo.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
public class HttpServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    public static void main(String[] args) {
        LOGGER.info("Server starting");
        ConfigurationManager.getInstance().loadConfigFile("src/main/resources/http.json");
        Configuration conf  = ConfigurationManager.getInstance().getCurrentConfiguration();
        LOGGER.info("Using port: "+ conf.getPort());
        LOGGER.info("Using Web Root: " + conf.getWebroot());
        ServerListenerThread serverListenerThread = null;
        try {
            // 하나의 스레드에서 요청이 끝나지 못하면 해당 큐에 있는 나머지 요청은 처리 하지 못한다.
            serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverListenerThread.start();
    }
}
