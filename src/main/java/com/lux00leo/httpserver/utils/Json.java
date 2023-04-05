package com.lux00leo.httpserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.lux00leo.httpserver.config.HttpConfigurationException;

import java.io.IOException;

public class Json {
    private static ObjectMapper myObjectMapper= defaultObjectMapper();
    private static ObjectMapper defaultObjectMapper(){
        ObjectMapper om = new ObjectMapper();
        // 찾는 프로퍼티 없을때 에러 나지 않도록
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return om;
    }
    public static JsonNode parse(String jsonSrc){
        try {
            return myObjectMapper.readTree(jsonSrc);
        } catch (IOException e) {
            throw new HttpConfigurationException("parsing configuration file error");
        }
    }
    public static <A> A fromJson(JsonNode node, Class <A> clazz) throws JsonProcessingException {
        return myObjectMapper.treeToValue(node,clazz);
    }
    public static JsonNode toJson(Object obj){
        return myObjectMapper.valueToTree(obj);
    }
    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node,false);
    }
    public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node,true);
    }
    private static String generateJson(Object o,boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = myObjectMapper.writer();
        if (pretty){
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(o);
    }
}
