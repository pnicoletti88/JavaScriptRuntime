package com;

import com.Data.Data;
import com.Data.DataReturnPacket;
import com.Functions.Function;
import com.Scopes.Scope;
import com.SpecialBlock.SpecialBlockRunner;
import com.SpecialBlock.SpecialBlockRunnerFactory;
import com.Util.StringHelpers;

public class Executor {
    private String code;
    private Scope scope;
    private int codeIndex;
    private int nextLineStartIndex;

    private static final SpecialBlockRunnerFactory blockRunnerFactory = new SpecialBlockRunnerFactory();

    public Executor(String code, Scope scope) {
        this.code = code;
        this.scope = scope;
    }

    public DataReturnPacket run() throws Exception {
        codeIndex = 0;
        nextLineStartIndex = 0;

        for (; codeIndex < code.length(); codeIndex++) {
            char curr = code.charAt(codeIndex);
            if (curr == ';' || curr == '{') {
                String line = code.substring(nextLineStartIndex, codeIndex).trim();
                if (blockRunnerFactory.isStringStartSpecialBlockRunnerType(line)) {
                    DataReturnPacket result = buildAndRunBlockRunner(line);
                    if (result.wasReturnCalled()) {
                        return result;
                    }
                } else if (isReturnStatement(line)) {
                    return executeReturnStatement(line);
                } else if (isFunctionDeclaration(line)) {
                    declareFunction();
                } else {
                    runStandardLine(line);
                }
                nextLineStartIndex = codeIndex + 1;
            }
        }

        return new DataReturnPacket(new Data(), false);
    }

    private String findCodeBlockAndIncreaseCodeIndex() throws Exception{
        int blockEndIndex = StringHelpers.findFirstAndLastBracketIndex(code, '{', '}', codeIndex)[1];
        String blockCode = code.substring(nextLineStartIndex, blockEndIndex + 1).trim();
        codeIndex = blockEndIndex;
        return blockCode;
    }

    private DataReturnPacket buildAndRunBlockRunner(String line) throws Exception{
        String codeBlock = findCodeBlockAndIncreaseCodeIndex();
        SpecialBlockRunner loop = blockRunnerFactory.createSpecialBlockRunner(line, codeBlock, scope);
        return loop.run();
    }

    private boolean isFunctionDeclaration(String str) {
        return str.length() >= 9 && str.substring(0, 9).equals("function ");
    }

    private void declareFunction() throws Exception{
        String codeBlock = findCodeBlockAndIncreaseCodeIndex();
        Function.declareFunction(codeBlock, scope);
    }

    private boolean isReturnStatement(String str) {
        return str.length() >= 7 && str.substring(0, 7).equals("return ");
    }

    private DataReturnPacket executeReturnStatement(String line) throws Exception{
        String standardLine = line.substring(7);
        Data runResult = runStandardLine(standardLine);
        return new DataReturnPacket(runResult, true);
    }

    private Data runStandardLine(String line) throws Exception{
        CodeLine codeLine = new CodeLine(line, scope);
        return codeLine.runAndReturnResult();
    }

}
