package com;

import com.Data.Data;
import com.Scopes.Scope;
import com.Util.StringHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeLine {
    private String line;
    private Scope scope;

    public CodeLine(String line, Scope scope){
        this.line = line;
        this.scope = scope;
    }

    public Data runAndReturnResult() throws Exception {
        List<Integer> equalsIndex = StringHelpers.singleEqualsIndexes(line);
        if(equalsIndex.size() > 1){
            throw new Exception("Invalid Multiple Assignment");
        } else if (equalsIndex.size() == 1){
            String lhs = line.substring(0, equalsIndex.get(0)).trim();
            String rhs = line.substring(equalsIndex.get(0) + 1).trim();
            return performAssignment(lhs, rhs);
        } else {
            return parseRightHandSide(line);
        }
    }

    private Data performAssignment(String leftHandSide, String rightHandSide) throws Exception {
        String variableName = parseLeftHandSide(leftHandSide);
        Data result = parseRightHandSide(rightHandSide);
        if(isDecleration(leftHandSide)){
            scope.createVariable(variableName, result);
        } else {
            scope.updateVariable(variableName, result);
        }
        return new Data();
    }

    private String parseLeftHandSide(String leftHandSide){
        if (isDecleration(leftHandSide)){
            return leftHandSide.replace("let ", "");
        }
        return leftHandSide;
    }

    private boolean isDecleration(String leftSide){
        return leftSide.contains("let ");
    }

    private Data parseRightHandSide(String rightHandSide) throws Exception{
        ArrayList<String> splitExpression = Operands.splitOnOperand(rightHandSide);
        ArrayList<String> operands = Operands.parseOperands(rightHandSide);
        Data[] dataItems = new Data[splitExpression.size()];
        for(int i=0; i<splitExpression.size(); i++){
            if(splitExpression.get(i).equals("")){
                throw new Exception("Poorly formatted expression");
            }
            dataItems[i] = parseSingleItem(splitExpression.get(i).trim());
        }
        return evaluateExpression(Arrays.asList(dataItems), operands);
    }

    private Data parseSingleItem(String item) throws Exception{
        if (isPrimitive(item)){
            return new Data(item);
        } else {
            Data currentData = scope.getVariable(item);
            String currentDataString = currentData.getData().toString();
            return new Data(currentDataString, currentData.getType());
        }
    }

    private boolean isPrimitive(String word){
        boolean isString = word.contains("\"");
        boolean isNumber = word.length() > 0 && Character.isDigit(word.charAt(0));
        boolean isBoolean = word.equals("True") || word.equals("False");
        return  isString || isNumber || isBoolean;
    }

    private Data evaluateExpression(List<Data> dataElements, List<String> operators) throws Exception{
        if((dataElements.size() - operators.size()) != 1){
            throw new Exception("Poorly formatted expression");
        }
        Data current = dataElements.get(0);
        for(int i=1; i<dataElements.size(); i++){
            current = Operands.evaluate(current, dataElements.get(i), operators.get(i-1));
        }
        return current;
    }

}
