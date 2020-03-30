package com;

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

    public void run() throws Exception {
        int prev = 0;
        for (int i = 0; i < code.length(); i++) {
            char curr = code.charAt(i);
            if (curr == ';' || curr == '{') {
                String line = code.substring(prev, i).trim();

                if (isForLoop(line)) {
                    int blockEndIndex = StringHelpers.findFirstAndLastBracketIndex(code, '{', '}', i)[1];
                    String blockCode = code.substring(prev, blockEndIndex+1);
                    Loop loop = new ForLoop(blockCode, scope);
                    loop.run();
                    i = blockEndIndex;
                } else {
                    CodeLine codeLine = new CodeLine(line, scope);
                    codeLine.runAndReturnResult();
                }
                prev = i + 1;
            }
        }
    }

    private boolean isForLoop(String str){
        return str.length() >= 4 && (str.substring(0, 4).equals("for ") || str.substring(0, 4).equals("for("));
    }
}
