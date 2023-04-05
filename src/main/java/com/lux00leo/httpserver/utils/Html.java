package com.lux00leo.httpserver.utils;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Html {
    public static String writeHTML (String filePath){
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String tmp_str;
            while ((tmp_str = in.readLine()) != null){
                contentBuilder.append(tmp_str);
            }
            in.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contentBuilder.toString();
    }
}
