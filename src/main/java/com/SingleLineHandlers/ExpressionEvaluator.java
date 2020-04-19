package com.SingleLineHandlers;

import com.Data.Data;
import com.Data.Operands;
import com.Functions.Function;
import com.Scopes.Scope;
import com.Util.StringHelpers;

import java.util.ArrayList;
import java.util.List;

public class ExpressionEvaluator {
    private String line;
    private Scope scope;
    private Data result;
    private List<Data> splitExpression = new ArrayList<>();
    private List<String> operands = new ArrayList<>();
    private StringBuilder currentChars = new StringBuilder();
    private int currentIndex = 0;

    ExpressionEvaluator(String line, Scope scope) throws Exception {
        this.line = line;
        this.scope = scope;
        this.result = evaluate();
    }

    private Data evaluate() throws Exception {
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
            splitExpression.add(stringToData(currentChars.toString().trim()));
        }
        return evaluateExpression(splitExpression, operands);
    }

    private void addQuotedStringToCurrentChars() throws Exception{
        String s = StringHelpers.findQuotedString(line, currentIndex);
        currentIndex += s.length() - 1;
        currentChars.append(s);
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
        operands.add(line.substring(currentIndex, currentIndex + operandLength));
        currentIndex += operandLength - 1;
        if (!isStringBlank(currentChars)) {
            splitExpression.add(stringToData(currentChars.toString().trim()));
        }
        currentChars = new StringBuilder();
    }

    private int getOperandLength(String s, int startIndex) throws Exception {
        if (Operands.isOperand(s.substring(startIndex, startIndex + 1))) {
            return 1;
        } else if (Operands.isOperand(s.substring(startIndex, startIndex + 2))) {
            return 2;
        } else {
            throw new Exception("Cannot get length of invalid operand");
        }
    }

    public boolean isStringBlank(StringBuilder s) {
        return s.toString().trim().equals("");
    }

    public Data stringToData(String s) throws Exception {
        if (s.equals("")) {
            throw new Exception("Poorly formatted expression");
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
        int[] bracketIndex = StringHelpers.findFirstAndLastBracketIndex(line, '(', ')', currentIndex);
        Data eval;
        if (currentCharsHasFunctionName()) {
            String funcCall = currentChars.toString().trim() + line.substring(bracketIndex[0], bracketIndex[1] + 1);
            currentChars = new StringBuilder();
            eval = new Function(funcCall, scope).executeFunction();
        } else {
            String bracketRegion = line.substring(bracketIndex[0] + 1, bracketIndex[1]);
            eval = new ExpressionEvaluator(bracketRegion, scope).getResult();
        }
        splitExpression.add(eval);
        currentIndex = bracketIndex[1];
    }

    private boolean currentCharsHasFunctionName(){
        return !currentChars.toString().trim().equals("");
    }

    private Data evaluateExpression(List<Data> dataElements, List<String> operators) throws Exception {
        if ((dataElements.size() - operators.size()) != 1) {
            throw new Exception("Poorly formatted expression");
        }
        Data current = dataElements.get(0);
        // TODO order of ops
        for (int i = 1; i < dataElements.size(); i++) {
            current = Operands.evaluate(current, dataElements.get(i), operators.get(i - 1));
        }
        return current;
    }

    public Data getResult() {
        return result;
    }
}
