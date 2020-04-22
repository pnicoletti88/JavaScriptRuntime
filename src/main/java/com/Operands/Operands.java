package com.Operands;

import com.Data.Data;
import com.Data.DataTypes;
import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.Exceptions.InternalErrorCodes;
import com.Exceptions.InternalException;

import java.util.HashMap;

public class Operands {
    private static String[] operands = new String[]{"+", "-", "*", "/", "==", "!=", ">", "<", "<=", ">=", "&&", "||"};
    private static HashMap<String, Integer> operandPriority = null;

    public static Operand operandFactory(String op) throws Exception{
        if(operandPriority == null){
            setOperandPriority();
        }
        if(!operandPriority.containsKey(op)){
            throw new InternalException(InternalErrorCodes.UNRECOGNIZED_OPERAND);
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
            String expression = a.getType().toString() + op.getOperand() + a.getType().toString();
            throw new ExternalException(ExternalErrorCodes.TYPE_MISALIGNMENT, expression);
        }
        if (a.getType() == DataTypes.Number) {
            return evaluateNumbers(a, b, op);
        } else if (a.getType() == DataTypes.String) {
            return evaluateStrings(a, b, op);
        } else if (a.getType() == DataTypes.Boolean) {
            return evaluateBoolean(a, b, op);
        }
        throw new InternalException(InternalErrorCodes.UNREACHABLE_CODE);
    }

    private static Data evaluateNumbers(Data a, Data b, Operand op) throws Exception {
        if (a.getType() != DataTypes.Number || b.getType() != DataTypes.Number) {
            throw new InternalException(InternalErrorCodes.TYPE_MISMATCH);
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
            default:
                String expression = "Number " + op.getOperand() + " Number";
                throw new ExternalException(ExternalErrorCodes.UNSUPPORTED_OPERAND, expression);
        }
    }

    private static Data evaluateStrings(Data a, Data b, Operand op) throws Exception {
        if (a.getType() != DataTypes.String || b.getType() != DataTypes.String) {
            throw new InternalException(InternalErrorCodes.TYPE_MISMATCH);
        }
        if (!op.getOperand().equals("+")) {
            String expression = "String " + op.getOperand() + " String";
            throw new ExternalException(ExternalErrorCodes.UNSUPPORTED_OPERAND, expression);
        }
        return new Data((String) a.getData() + (String) b.getData(), DataTypes.String);
    }

    private static Data evaluateBoolean(Data a, Data b, Operand op) throws Exception {
        if (a.getType() != DataTypes.Boolean || b.getType() != DataTypes.Boolean) {
            throw new InternalException(InternalErrorCodes.TYPE_MISMATCH);
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
            default:
                String expression = "Boolean " + op.getOperand() + " Boolean";
                throw new ExternalException(ExternalErrorCodes.UNSUPPORTED_OPERAND, expression);
        }
    }
}
