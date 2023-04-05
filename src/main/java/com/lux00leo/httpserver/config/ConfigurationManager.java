package com.lux00leo.httpserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.lux00leo.httpserver.utils.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    private static ConfigurationManager myConfigManager;
    public static Configuration myCurrentConfiguration;
    private ConfigurationManager(){}
    public static ConfigurationManager getInstance(){
        if (myConfigManager == null){
            myConfigManager = new ConfigurationManager();
        }
        return myConfigManager;
    }
    public void loadConfigFile(String filePath) {
        FileReader fileReader;
        try {
             fileReader = new FileReader(filePath);
        }catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        while (true){
            try {
                if (!((i = fileReader.read()) != -1)) break;
            } catch (IOException e) {
                throw new HttpConfigurationException(e);
            }
            sb.append((char)i);
        }
        JsonNode conf = Json.parse(sb.toString());
        try {
            myCurrentConfiguration = Json.fromJson(conf,Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("json file parsing configuration files error, internal",e);
        }
    }
    public Configuration getCurrentConfiguration(){
        if (myCurrentConfiguration == null){
            throw new HttpConfigurationException("No Current Configuration set!");
        }
        return myCurrentConfiguration;
    }
}
