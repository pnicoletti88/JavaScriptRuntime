package com.Data;

import com.Data.Data;
import com.Data.DataTypes;

import java.util.ArrayList;

public class Operands {
    private static String[] operands = new String[]{"+", "-", "*", "/","==", "!=", ">", "<", "<=", ">=", "&&", "||"};

    public static boolean isOperand(String str){
        for(String operand:operands){
            if(str.equals(operand)){
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> splitOnOperand(String s){
        ArrayList<String> output = new ArrayList<>();
        int prev = 0;
        boolean isInQuotes = false;
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i) == '"'){
                isInQuotes = !isInQuotes;
            }
            if(!isInQuotes){
                if(i < (s.length() - 1) && isOperand(s.substring(i, i+2))){
                    output.add(s.substring(prev, i));
                    prev = i+2;
                    i += 1;
                } else if (isOperand(s.substring(i, i+1))){
                    output.add(s.substring(prev, i));
                    prev = i+1;
                }
            }
        }
        output.add(s.substring(prev));
        return output;
    }

    public static ArrayList<String> parseOperands(String s){
        ArrayList<String> output = new ArrayList<>();
        boolean isInQuotes = false;
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i) == '"'){
                isInQuotes = !isInQuotes;
            }
            if(!isInQuotes){
                if(i < (s.length() - 1) && isOperand(s.substring(i, i+2))){
                    output.add(s.substring(i, i+2));
                    i += 1;
                } else if (isOperand(s.substring(i, i+1))){
                    output.add(s.substring(i, i+1));
                }
            }
        }
        return output;
    }

    public static Data evaluate(Data a, Data b, String operand) throws Exception{
        if(a.getType() != b.getType()){
            throw new Exception("Math can only be performed on the same type");
        }
        if(a.getType() == DataTypes.Number){
            return evaluateNumbers(a,b,operand);
        } else if(a.getType() == DataTypes.String){
            return evaluateStrings(a,b,operand);
        } else if(a.getType() == DataTypes.Boolean){
            return  evaluateBoolean(a,b,operand);
        }
        throw new Exception("Unknow Data Type To Evaluate");
    }

    private static Data evaluateNumbers(Data a, Data b, String operand) throws Exception{
        if(a.getType() != DataTypes.Number || b.getType() != DataTypes.Number){
            throw new Exception("Internal Error: evaluating numbers");
        }
        switch (operand) {
            case "+":
                return new Data((Double) a.getData() + (Double) b.getData());
            case "-":
                return new Data((Double) a.getData() - (Double) b.getData());
            case "*":
                return new Data((Double) a.getData() * (Double) b.getData());
            case "/":
                return new Data((Double) a.getData() / (Double) b.getData());
            case "==":
                return new Data(((Double)a.getData()).equals((Double) b.getData()));
            case "!=":
                return new Data(!((Double)a.getData()).equals((Double) b.getData()));
            case "<=":
                return new Data((Double) a.getData() <= (Double) b.getData());
            case ">=":
                return new Data((Double) a.getData() >= (Double) b.getData());
            case "<":
                return new Data((Double) a.getData() < (Double) b.getData());
            case ">":
                return new Data((Double) a.getData() > (Double) b.getData());
        }
        throw new Exception("Unknown Operand");
    }

    private static Data evaluateStrings(Data a, Data b, String operand) throws Exception{
        if(a.getType() != DataTypes.String || b.getType() != DataTypes.String){
            throw new Exception("Internal Error: evaluating strings");
        }
        if(!operand.equals("+")){
            throw new Exception("Error: can only add strings");
        }
        return new Data((String)a.getData() + (String)b.getData(), DataTypes.String);
    }

    private static Data evaluateBoolean(Data a, Data b, String operand) throws Exception{
        if(a.getType() != DataTypes.Boolean || b.getType() != DataTypes.Boolean){
            throw new Exception("Internal Error: evaluating numbers");
        }
        switch (operand) {
            case "==":
                return new Data((Boolean) a.getData() == (Boolean) b.getData());
            case "!=":
                return new Data((Boolean) a.getData() != (Boolean) b.getData());
            case "&&":
                return new Data((Boolean) a.getData() && (Boolean) b.getData());
            case "||":
                return new Data((Boolean) a.getData() || (Boolean) b.getData());
        }
        throw new Exception("Unknown Operand");
    }
}
