package com;

import com.Data.Data;
import com.Data.DataReturnPacket;
import com.Functions.Function;
import com.ScopedBlock.ScopedBlockRunnerFactory;
import com.Scopes.Scope;
import com.ScopedBlock.ScopedBlockRunner;
import com.ScopedBlock.ScopedBlockRunnerFactory.*;
import com.Util.StringHelpers;

public class Executor {
    private String code;
    private Scope scope;
    private int codeIndex;
    private StringBuilder currentExpression;

    private static final DataReturnPacket defaultDataReturnPack = new DataReturnPacket();
    private static final ScopedBlockRunnerFactory blockRunnerFactory = new ScopedBlockRunnerFactory();

    public Executor(String code, Scope scope) {
        this.code = code;
        this.scope = scope;
    }

    public DataReturnPacket run() throws Exception {
        codeIndex = 0;
        currentExpression = new StringBuilder();

        for (; codeIndex < code.length(); codeIndex++) {
            DataReturnPacket executionResult = null;

            addCharToCurrentExpression(code.charAt(codeIndex));

            if(isAbleToEvaluateCurrentExpression()){
                executionResult = evaluateCurrentExpression();
            }

            if (executionResult != null && executionResult.wasReturnCalled()) {
                return executionResult;
            }
        }
        return new DataReturnPacket(new Data(), false);
    }

    private void addCharToCurrentExpression(char c){
        boolean isCharValid = currentExpression.length() > 0 || c != ' ';
        if(isCharValid){
            currentExpression.append(c);
        }
    }

    private boolean isAbleToEvaluateCurrentExpression(){
        boolean result = isLastCharOfCurrentExpressionSemiColon();
        result = result || blockRunnerFactory.isSpecialBlockRunnerType(currentExpression.toString());
        result = result || isFunctionDeclaration(currentExpression.toString());
        return result;
    }

    private boolean isLastCharOfCurrentExpressionSemiColon(){
        return currentExpression.length() > 0 && currentExpression.charAt(currentExpression.length() -1) == ';';
    }

    private DataReturnPacket evaluateCurrentExpression() throws Exception{
        DataReturnPacket executionResult = null;
        if (isLastCharOfCurrentExpressionSemiColon()) {
            executionResult = runCurrentLine();
        } else if (blockRunnerFactory.isSpecialBlockRunnerType(currentExpression.toString())) {
            ScopedBlockRunnerTypes type = blockRunnerFactory.getType(currentExpression.toString());
            executionResult = buildAndRunBlockRunner(type);
        } else if (isFunctionDeclaration(currentExpression.toString())) {
            declareFunction();
        }
        currentExpression = new StringBuilder();
        return executionResult;
    }

    private DataReturnPacket runCurrentLine() throws Exception {
        DataReturnPacket result = defaultDataReturnPack;
        String lineWithOutSemiColon = currentExpression.toString().substring(0, currentExpression.length() - 1);
        if (isReturnStatement(lineWithOutSemiColon)) {
            result = executeReturnStatement(lineWithOutSemiColon);
        } else {
            runStandardLine(lineWithOutSemiColon);
        }
        return result;
    }

    private boolean isReturnStatement(String str) {
        return str.length() >= 7 && str.substring(0, 7).equals("return ");
    }

    private int currentLineStartIndex() {
        return codeIndex - currentExpression.length() + 1;
    }

    private DataReturnPacket buildAndRunBlockRunner(ScopedBlockRunnerTypes type) throws Exception {
        String codeBlock = blockRunnerFactory.parseBlock(type, code, currentLineStartIndex());
        codeIndex = currentLineStartIndex() + codeBlock.length();
        ScopedBlockRunner loop = blockRunnerFactory.createSpecialBlockRunner(type, codeBlock, scope);
        return loop.run();
    }

    private boolean isFunctionDeclaration(String str) {
        return str.length() >= 9 && str.substring(0, 9).equals("function ");
    }

    private void declareFunction() throws Exception {
        String codeBlock = findCodeBlockAndIncreaseCodeIndex();
        Function.declareFunction(codeBlock, scope);
    }

    private String findCodeBlockAndIncreaseCodeIndex() throws Exception {
        int blockEndIndex = StringHelpers.findFirstAndLastBracketIndex(code, '{', '}', codeIndex)[1];
        String blockCode = code.substring(currentLineStartIndex(), blockEndIndex + 1).trim();
        codeIndex = blockEndIndex;
        return blockCode;
    }

    private DataReturnPacket executeReturnStatement(String line) throws Exception {
        String standardLine = line.substring(7);
        Data runResult = runStandardLine(standardLine);
        return new DataReturnPacket(runResult, true);
    }

    private Data runStandardLine(String line) throws Exception {
        CodeLine codeLine = new CodeLine(line, scope);
        return codeLine.runAndReturnResult();
    }
}
