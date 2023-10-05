package com.javayh.agent.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author haiji
 */
public class ReadResourceFile {


    public static void print() {
        // 获取当前类的ClassLoader
        ClassLoader classLoader = ReadResourceFile.class.getClassLoader();

        // 读取资源文件
        try (InputStream inputStream = classLoader.getResourceAsStream("banner.txt")) {
            if (inputStream != null) {
                // 使用BufferedReader读取文件内容
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
