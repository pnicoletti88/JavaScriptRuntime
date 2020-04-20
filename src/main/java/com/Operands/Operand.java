package com.Operands;

public class Operand {
    private int priority;
    private String operand;

    public Operand(int priority, String operand){
        this.priority = priority;
        this.operand = operand;
    }

    public int getPriority() {
        return priority;
    }

    public String getOperand() {
        return operand;
    }
}
