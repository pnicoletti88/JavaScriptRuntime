package com.Functions.BuiltInFunctions;

import com.Data.Data;
import com.Data.DataTypes;
import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.Scopes.Scope;
import com.Process;
import com.Task;

import java.util.ArrayList;
import java.util.List;

public class SetTimeOut implements BuiltInFunction {
    private class SetTimeOutAsync implements Runnable{
        private final Scope callBackScope;
        private final Data callBack;
        private final int sleepTime;
        private final Process process;

        public SetTimeOutAsync(Data callBackCode, Scope callBackScope, int sleepTime, Process process){
            this.callBack = callBackCode;
            this.callBackScope = callBackScope;
            this.sleepTime = sleepTime;
            this.process = process;
        }

        @Override
        public void run(){
            try{
                Thread.sleep(sleepTime);
            } catch(InterruptedException e){
                System.out.println("Thread issue | TODO how to solve");
            }

            try {
                Task completedTask = new Task(callBackScope, callBack, new ArrayList<>());
                process.addToEventQueue(completedTask);
            } catch(Exception e){
                System.out.println("thread error, learn how to move to main thread");
            }
        }
    }

    public Data run(List<Data> params, Scope callBackParentScope, Process process) throws Exception {
        validateTimeOutCall(params);
        int sleepTime = Math.max(((Double)params.get(1).getData()).intValue(), 0);
        SetTimeOutAsync runnable = new SetTimeOutAsync(params.get(0), callBackParentScope, sleepTime, process);
        process.addPendingTask();
        new Thread(runnable).start();
        return new Data();
    }

    private void validateTimeOutCall(List<Data> params) throws Exception{
        boolean isInvalid = params.size() < 2;
        isInvalid = isInvalid && (params.get(0).getType() == DataTypes.Function || params.get(0).getType() == DataTypes.BuiltInFunction);
        isInvalid = isInvalid && params.get(1).getType() == DataTypes.Number;
        if(isInvalid){
            throw new ExternalException(ExternalErrorCodes.ILLEGAL_ARGUMENT, "setTimeOut");
        }
    }
}
