package com.ivanonjava.AssistantAndHelper.helpers;

import com.ivanonjava.AssistantAndHelper.Constants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Info {

    public static String getInfo() {
        return "Author: @ " + Constants.AUTHOR +
                "\nPowered by: " + Constants.LANGUAGE +
                "\nVersion: " + Constants.VERSION_NAME +
                "\nEmail: " + Constants.EMAIL;
    }
    public static String getVersionInfo() {
        String info = "";
        StringBuilder builder = new StringBuilder(info);
        try {
            List<String> infList = Files.readAllLines(Paths.get("info.txt"), StandardCharsets.UTF_8);
            for(String line: infList){
                builder.append(line);
                builder.append("\n");
            }
            info = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }

}
