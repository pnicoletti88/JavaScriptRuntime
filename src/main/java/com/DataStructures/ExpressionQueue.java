package com.DataStructures;

import com.Data.Data;
import com.Exceptions.InternalErrorCodes;
import com.Exceptions.InternalException;
import com.Operands.Operand;

import java.util.LinkedList;
import java.util.Queue;


public class ExpressionQueue {
    private Queue<Operand> ops = new LinkedList<>();
    private Queue<Data> datas = new LinkedList<>();
    private boolean isDataNextToBeAdded = true;

    public void add(Operand op) throws Exception{
        if(isDataNextToBeAdded){
            throw new InternalException(InternalErrorCodes.ILLEGAL_EXPRESSION_QUEUE_ADD);
        }
        ops.add(op);
        isDataNextToBeAdded = true;
    }

    public void add(Data d) throws Exception{
        if(!isDataNextToBeAdded){
            throw new InternalException(InternalErrorCodes.ILLEGAL_EXPRESSION_QUEUE_ADD);
        }
        datas.add(d);
        isDataNextToBeAdded = false;
    }

    public Operand pollOperand() throws Exception{
        if(ops.size() != datas.size()){
            throw new InternalException(InternalErrorCodes.ILLEGAL_EXPRESSION_QUEUE_POLL);
        }
        return ops.poll();
    }

    public Data pollData() throws Exception{
        if(ops.size() == datas.size()){
            throw new InternalException(InternalErrorCodes.ILLEGAL_EXPRESSION_QUEUE_POLL);
        }
        return datas.poll();
    }

    public boolean isInValidStateForEvaluation(){
        return datas.size() - 1 == ops.size();
    }

    public boolean isEmpty(){
        return datas.isEmpty();
    }
}
