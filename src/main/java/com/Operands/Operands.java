package com.Operands;

import com.Data.Data;
import com.Data.DataTypes;

import java.util.HashMap;

public class Operands {
    private static String[] operands = new String[]{"+", "-", "*", "/", "==", "!=", ">", "<", "<=", ">=", "&&", "||"};
    private static HashMap<String, Integer> operandPriority = null;

    public static Operand operandFactory(String op) throws Exception{
        if(operandPriority == null){
            setOperandPriority();
        }
        if(!operandPriority.containsKey(op)){
            throw new Exception("Invalid Operand");
        }
        return new Operand(operandPriority.get(op), op);

    }

    private static void setOperandPriority(){
        operandPriority = new HashMap<>();
        operandPriority.put("*", 1);
        operandPriority.put("/", 1);
        operandPriority.put("+", 2);
        operandPriority.put("-", 2);
        operandPriority.put("==", 3);
        operandPriority.put("!=", 3);
        operandPriority.put(">", 3);
        operandPriority.put("<", 3);
        operandPriority.put("<=", 3);
        operandPriority.put(">=", 3);
        operandPriority.put("&&", 3);
        operandPriority.put("||", 3);
    }

    public static boolean isOperand(String str) {
        for (String operand : operands) {
            if (str.equals(operand)) {
                return true;
            }
        }
        return false;
    }

    public static Data evaluate(Data a, Data b, Operand op) throws Exception {
        if (a.getType() != b.getType()) {
            throw new Exception("Math can only be performed on the same type");
        }
        if (a.getType() == DataTypes.Number) {
            return evaluateNumbers(a, b, op);
        } else if (a.getType() == DataTypes.String) {
            return evaluateStrings(a, b, op);
        } else if (a.getType() == DataTypes.Boolean) {
            return evaluateBoolean(a, b, op);
        }
        throw new Exception("Unknown Data Type To Evaluate");
    }

    private static Data evaluateNumbers(Data a, Data b, Operand op) throws Exception {
        if (a.getType() != DataTypes.Number || b.getType() != DataTypes.Number) {
            throw new Exception("Internal Error: evaluating numbers");
        }
        switch (op.getOperand()) {
            case "+":
                return new Data((Double) a.getData() + (Double) b.getData());
            case "-":
                return new Data((Double) a.getData() - (Double) b.getData());
            case "*":
                return new Data((Double) a.getData() * (Double) b.getData());
            case "/":
                return new Data((Double) a.getData() / (Double) b.getData());
            case "==":
                return new Data(((Double) a.getData()).equals((Double) b.getData()));
            case "!=":
                return new Data(!((Double) a.getData()).equals((Double) b.getData()));
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

    private static Data evaluateStrings(Data a, Data b, Operand op) throws Exception {
        if (a.getType() != DataTypes.String || b.getType() != DataTypes.String) {
            throw new Exception("Internal Error: evaluating strings");
        }
        if (!op.getOperand().equals("+")) {
            throw new Exception("Error: can only add strings");
        }
        return new Data((String) a.getData() + (String) b.getData(), DataTypes.String);
    }

    private static Data evaluateBoolean(Data a, Data b, Operand op) throws Exception {
        if (a.getType() != DataTypes.Boolean || b.getType() != DataTypes.Boolean) {
            throw new Exception("Internal Error: evaluating numbers");
        }
        switch (op.getOperand()) {
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
