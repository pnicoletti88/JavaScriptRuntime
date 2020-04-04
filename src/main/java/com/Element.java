package com;

import com.Data.Data;
import com.Functions.Function;
import com.Scopes.Scope;
import com.Util.StringHelpers;

public class Element {
    private String element;
    private Scope scope;

    public Element(String element, Scope scope){
        this.element = element;
        this.scope = scope;
    }

    public Data convertElementToData() throws Exception{
        if (isPrimitive()){
            return new Data(element);
        } else if(isFunctionCall()) {
            Function func = new Function(element, scope);
            return func.executeFunction();
        } else {
            Data currentData = scope.getVariable(element);
            String currentDataString = currentData.getData().toString();
            return new Data(currentDataString, currentData.getType());
        }
    }

    public boolean isPrimitive(){
        boolean isString = element.contains("\"");
        boolean isNumber = element.length() > 0 && Character.isDigit(element.charAt(0));
        boolean isBoolean = element.equals("True") || element.equals("False");
        return  isString || isNumber || isBoolean;
    }

    public boolean isFunctionCall(){
        boolean hasFunctionCallBrackets = element.indexOf('(') != -1;
        if(!hasFunctionCallBrackets){
            return false;
        }
        try{
            int[] bracketIndex = StringHelpers.findFirstAndLastBracketIndex(element, '(', ')');
            boolean isLastCharCloseBracket = bracketIndex[1] == (element.length() - 1);
            boolean singleWord = element.indexOf(' ') > bracketIndex[0] || element.indexOf(' ') == -1;
            boolean hasFunctionName = bracketIndex[0] > 0;
            return isLastCharCloseBracket && singleWord && hasFunctionName;
        } catch(Exception e){
            return false;
        }
    }
}
