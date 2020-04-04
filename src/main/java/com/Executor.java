package com;

import com.Data.Data;
import com.Functions.Function;
import com.Loops.ForLoop;
import com.Loops.Loop;
import com.Scopes.Scope;
import com.Util.StringHelpers;

public class Executor {
    private String code;
    private Scope scope;
    public Executor(String code, Scope scope){
        this.code = code;
        this.scope = scope;
    }

    public Data run() throws Exception {
        int prev = 0;
        for (int i = 0; i < code.length(); i++) {
            char curr = code.charAt(i);
            if (curr == ';' || curr == '{') {
                String line = code.substring(prev, i).trim();
                boolean forLoop = isForLoop(line);
                boolean functionDecleration = isFunctionDecleration(line);
                if (forLoop || functionDecleration) {
                    int blockEndIndex = StringHelpers.findFirstAndLastBracketIndex(code, '{', '}', i)[1];
                    String blockCode = code.substring(prev, blockEndIndex + 1).trim();
                    if(forLoop){
                        Loop loop = new ForLoop(blockCode, scope);
                        loop.run();
                    } else if(functionDecleration){
                        Function.declareFunction(blockCode, scope);
                    }
                    i = blockEndIndex;
                } else if(isReturnStatement(line)){
                    line = line.substring(7);
                    CodeLine codeLine = new CodeLine(line, scope);
                    return codeLine.runAndReturnResult();
                } else {
                    CodeLine codeLine = new CodeLine(line, scope);
                    codeLine.runAndReturnResult();
                }
                prev = i + 1;
            }
        }
        return new Data();
    }

    private boolean isForLoop(String str){
        return str.length() >= 4 && (str.substring(0, 4).equals("for ") || str.substring(0, 4).equals("for("));
    }

    private boolean isFunctionDecleration(String str){
        return str.length() >= 9 && str.substring(0, 9).equals("function ");
    }

    private boolean isReturnStatement(String str){
        return str.length() >= 7 && str.substring(0, 7).equals("return ");
    }

}
