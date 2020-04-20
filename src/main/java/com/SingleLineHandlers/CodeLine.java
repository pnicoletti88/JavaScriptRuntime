package com.SingleLineHandlers;

import com.Data.Data;
import com.Scopes.Scope;
import com.Util.StringHelpers;

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
        if(isDeclaration(leftHandSide)){
            scope.createVariable(variableName, result);
        } else {
            scope.updateVariable(variableName, result);
        }
        return new Data();
    }

    private String parseLeftHandSide(String leftHandSide){
        if (isDeclaration(leftHandSide)){
            return leftHandSide.replace("let ", "");
        }
        return leftHandSide;
    }

    private boolean isDeclaration(String leftSide){
        return leftSide.contains("let ");
    }

    private Data parseRightHandSide(String rightHandSide) throws Exception{
        ExpressionEvaluator ex = new ExpressionEvaluator(rightHandSide, scope);
        return ex.getResult();
    }
}
