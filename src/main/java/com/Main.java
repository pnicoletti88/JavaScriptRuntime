package com;

import com.Util.FileReader;
import com.Util.StringHelpers;

public class Main {
    public static void main(String[] args) throws Exception{
        String filePath = System.getProperty("user.dir") + "\\testFile.txt";
        String code = FileReader.convertTxtFileToString(filePath);
        String cleanedCode = StringHelpers.removeNewLines(code);
        Process exec = new Process(cleanedCode);
        exec.start();
    }
}
