package com.Util;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {
    public static String convertTxtFileToString(String fileName) throws Exception{
        String data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }
}
