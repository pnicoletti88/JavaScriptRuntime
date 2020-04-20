package com.DataStructures;

import com.Data.Data;
import com.Operands.Operand;

import java.util.LinkedList;
import java.util.Queue;


public class ExpressionQueue {
    private Queue<Operand> ops = new LinkedList<>();
    private Queue<Data> datas = new LinkedList<>();
    private boolean isDataNextToBeAdded = true;

    public Queue<Operand> getOps() {
        return ops;
    }

    public Queue<Data> getDatas() {
        return datas;
    }

    public void add(Operand op) throws Exception{
        if(isDataNextToBeAdded){
            throw new Exception("Illegal Expression Queue Addition");
        }
        ops.add(op);
        isDataNextToBeAdded = true;
    }

    public void add(Data d) throws Exception{
        if(!isDataNextToBeAdded){
            throw new Exception("Illegal Expression Queue Addition");
        }
        datas.add(d);
        isDataNextToBeAdded = false;
    }

    public Operand pollOperand() throws Exception{
        if(ops.size() != datas.size()){
            throw new Exception("Illegal Expression Queue Operand Poll");
        }
        return ops.poll();
    }

    public Data pollData() throws Exception{
        if(ops.size() == datas.size()){
            throw new Exception("Illegal Expression Queue Operand Poll");
        }
        return datas.poll();
    }

    public boolean isEmpty(){
        return datas.isEmpty();
    }
}
