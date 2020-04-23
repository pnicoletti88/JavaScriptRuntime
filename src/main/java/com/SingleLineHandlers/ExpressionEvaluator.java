package com.SingleLineHandlers;

import com.Data.Data;
import com.DataStructures.ExpressionQueue;
import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.Exceptions.InternalErrorCodes;
import com.Exceptions.InternalException;
import com.Operands.Operand;
import com.Operands.Operands;
import com.Functions.Function;
import com.Scopes.Scope;
import com.Util.StringHelpers;
import com.Process;

public class ExpressionEvaluator {
    private String line;
    private Scope scope;
    private Data result;
    private ExpressionQueue expQ = new ExpressionQueue();
    private StringBuilder currentChars = new StringBuilder();
    private int currentIndex = 0;
    private Process process;

    ExpressionEvaluator(String line, Scope scope, Process process) throws Exception {
        this.line = line;
        this.scope = scope;
        this.process = process;
        this.result = evaluate();
    }

    private Data evaluate() throws Exception {
        try{
            buildExpressionQueue();
        } catch (InternalException e){
            if(e.getCode() == InternalErrorCodes.ILLEGAL_EXPRESSION_QUEUE_ADD){
                throw new ExternalException(ExternalErrorCodes.ILLEGAL_EXPRESSION);
            }
            throw e;
        }
        return evaluateExpressionQueue();
    }

    public void buildExpressionQueue() throws Exception{
        for (; currentIndex < line.length(); currentIndex++) {
            if (line.charAt(currentIndex) == '"') {
                addQuotedStringToCurrentChars();
            } else if (isOperand(line, currentIndex)) {
                handleOperandAndCurrentChars();
            } else if (line.charAt(currentIndex) == '(') {
                handleParenthesisOpening();
            } else {
                currentChars.append(line.charAt(currentIndex));
            }
        }
        if (!currentChars.toString().trim().equals("")) {
            expQ.add(stringToData(currentChars.toString().trim()));
        }
    }

    private void addQuotedStringToCurrentChars() throws Exception{
        try {
            String s = StringHelpers.findQuotedString(line, currentIndex);
            currentIndex += s.length() - 1;
            currentChars.append(s);
        } catch(InternalException e){
            throw new ExternalException(ExternalErrorCodes.QUOTES_MISALIGNMENT);
        }
    }

    private boolean isOperand(String s, int startIndex) {
        boolean result = false;
        if (startIndex < s.length()) {
            result = Operands.isOperand(s.substring(startIndex, startIndex + 1));
        }
        if (startIndex + 1 < s.length()) {
            result = result || Operands.isOperand(s.substring(startIndex, startIndex + 2));
        }
        return result;
    }

    private void handleOperandAndCurrentChars() throws Exception{
        int operandLength = getOperandLength(line, currentIndex);
        Operand op = Operands.operandFactory(line.substring(currentIndex, currentIndex + operandLength));
        currentIndex += operandLength - 1;
        if (!isStringBlank(currentChars.toString())) {
            expQ.add(stringToData(currentChars.toString().trim()));
        }
        expQ.add(op);
        currentChars = new StringBuilder();
    }

    private int getOperandLength(String s, int startIndex) throws Exception {
        if (Operands.isOperand(s.substring(startIndex, startIndex + 1))) {
            return 1;
        } else if (Operands.isOperand(s.substring(startIndex, startIndex + 2))) {
            return 2;
        } else {
            throw new InternalException(InternalErrorCodes.UNRECOGNIZED_OPERAND);
        }
    }

    public boolean isStringBlank(String s) {
        return s.trim().equals("");
    }

    public Data stringToData(String s) throws Exception {
        if (isStringBlank(s)) {
            throw new ExternalException(ExternalErrorCodes.ILLEGAL_EXPRESSION, "invalid line: " + line);
        }
        if (isPrimitive(s)) {
            return new Data(s);
        } else {
            Data currentData = scope.getVariable(s);
            String currentDataString = currentData.getData().toString();
            return new Data(currentDataString, currentData.getType());
        }
    }

    public boolean isPrimitive(String s) {
        boolean isString = s.contains("\"");
        boolean isNumber = s.length() > 0 && Character.isDigit(s.charAt(0));
        boolean isBoolean = s.equals("True") || s.equals("False");
        return isString || isNumber || isBoolean;
    }

    private void handleParenthesisOpening() throws Exception{
        int[] bracketIndex;
        try {
            bracketIndex = StringHelpers.findFirstAndLastBracketIndex(line, '(', ')', currentIndex);
        } catch(InternalException e){
            throw new ExternalException(ExternalErrorCodes.ILLEGAL_EXPRESSION, line);
        }
        Data eval;
        if (currentCharsHasFunctionName()) {
            String funcCall = currentChars.toString().trim() + line.substring(bracketIndex[0], bracketIndex[1] + 1);
            currentChars = new StringBuilder();
            eval = new Function(funcCall, scope, process).executeFunction();
        } else {
            String bracketRegion = line.substring(bracketIndex[0] + 1, bracketIndex[1]);
            eval = new ExpressionEvaluator(bracketRegion, scope, process).getResult();
        }
        expQ.add(eval);
        currentIndex = bracketIndex[1];
    }

    private boolean currentCharsHasFunctionName(){
        return !currentChars.toString().trim().equals("");
    }

    private Data evaluateExpressionQueue() throws Exception {
        if(!expQ.isInValidStateForEvaluation()){
            throw new ExternalException(ExternalErrorCodes.ILLEGAL_EXPRESSION, line);
        }
        ExpressionQueue currentExpQ = expQ;
        ExpressionQueue nextQAfterEvaluatingCurrentPriority = new ExpressionQueue();
        Data current = null;
        for(int priorityLevel=1; priorityLevel <= 3; priorityLevel++){
            current = currentExpQ.pollData();

            while(!currentExpQ.isEmpty()) {
                Operand op = currentExpQ.pollOperand();
                Data d = currentExpQ.pollData();
                if(op.getPriority() == priorityLevel){
                    current = Operands.evaluate(current, d, op);
                } else {
                    nextQAfterEvaluatingCurrentPriority.add(current);
                    current = d;
                    nextQAfterEvaluatingCurrentPriority.add(op);
                }
            }
            nextQAfterEvaluatingCurrentPriority.add(current);
            currentExpQ = nextQAfterEvaluatingCurrentPriority;
            nextQAfterEvaluatingCurrentPriority = new ExpressionQueue();
        }
        return current;
    }

    public Data getResult() {
        return result;
    }
}
