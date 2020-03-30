package com.Loops;

import com.CodeLine;
import com.Data.Data;
import com.Data.DataTypes;
import com.Executor;
import com.Scopes.LoopScope;
import com.Scopes.Scope;

import java.util.List;

public abstract class Loop {
    private String code;
    private LoopScope scope;
    private CodeLine runCondition;
    private CodeLine increment;
    private Executor interiorCodeBlock;
    private List<String> parsedLoopCommands;
    private String loopBody;

    public Loop(String code, Scope parentScope) throws Exception {
        this.code = code.trim();
        scope = new LoopScope(parentScope);
    }

    public LoopScope getScope() {
        return scope;
    }

    public List<String> getParsedLoopCommands() {
        return parsedLoopCommands;
    }

    public String getLoopBody() {
        return loopBody;
    }

    public void setParsedLoopCommands(List<String> parsedLoopCommands) {
        this.parsedLoopCommands = parsedLoopCommands;
    }

    public void setLoopBody(String loopBody) {
        this.loopBody = loopBody;
    }

    public void setRunCondition(CodeLine runCondition) {
        this.runCondition = runCondition;
    }

    public void setIncrement(CodeLine increment) {
        this.increment = increment;
    }

    public void setInteriorCodeBlock(Executor interiorCodeBlock) {
        this.interiorCodeBlock = interiorCodeBlock;
    }

    public void run() throws Exception{
        boolean continueRunning = true;
        while (continueRunning) {
            if (evlatueRunCondition()) {
                runInteriorBlock();
                scope.resetLoopBody();
                executeIncrement();
            } else {
                continueRunning = false;
            }
        }
    }

    private Boolean evlatueRunCondition() throws Exception{
        if(runCondition != null){
            Data continueResult = runCondition.runAndReturnResult();
            if (continueResult.getType() != DataTypes.Boolean) {
                throw new Exception("Loop condition must be a boolean");
            }
            return (Boolean) continueResult.getData();
        } else {
            return true;
        }
    }

    private void runInteriorBlock() throws Exception{
        if(interiorCodeBlock != null){
            interiorCodeBlock.run();
        }
    }

    private void executeIncrement() throws Exception{
        if(increment != null){
            increment.runAndReturnResult();
        }
    }



}
