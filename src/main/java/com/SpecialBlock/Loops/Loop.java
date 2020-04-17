package com.SpecialBlock.Loops;

import com.CodeLine;
import com.Data.Data;
import com.Data.DataReturnPacket;
import com.Data.DataTypes;
import com.Executor;
import com.Scopes.LoopScope;
import com.Scopes.Scope;
import com.SpecialBlock.SpecialBlockRunner;
import com.Util.StringHelpers;

public abstract class Loop implements SpecialBlockRunner {
    private String loopDeclarationName;
    private String code;
    private LoopScope scope;
    private CodeLine runCondition;
    private CodeLine increment;
    private Executor interiorCodeBlock;

    public Loop(String code, Scope parentScope, String loopName) throws Exception {
        this.code = code.trim();
        scope = new LoopScope(parentScope);
        loopDeclarationName = loopName;
        validateLoopCall();
        loadInteriorCodeBlock();
    }

    private void validateLoopCall() throws Exception{
        if (!code.substring(0, loopDeclarationName.length()).equals(loopDeclarationName)) {
            throw new Exception("Internal loop error");
        }
        for(int i=3;i<code.length();i++){
            if(code.charAt(i) == '('){
                return;
            } else if(code.charAt(i) != ' '){
                throw new Exception("Invalid loop syntax");
            }
        }
    }

    private void loadInteriorCodeBlock() throws Exception {
        String loopBody =  StringHelpers.parseBlockBody(code);
        Executor interiorCodeBlock = new Executor(loopBody, scope);
        setInteriorCodeBlock(interiorCodeBlock);
    }

    public LoopScope getScope() {
        return scope;
    }


    public String getCode() {
        return code;
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

    public DataReturnPacket run() throws Exception {
        DataReturnPacket mostRecent = null;
        while (evaluateRunCondition()) {
            mostRecent = runInteriorBlock();
            if(mostRecent.wasReturnCalled()){
                return mostRecent;
            }
            scope.resetLoopBody();
            executeIncrement();
        }
        return mostRecent == null ? new DataReturnPacket() : mostRecent;
    }

    private Boolean evaluateRunCondition() throws Exception {
        if (runCondition == null) {
            return true;
        }

        Data continueResult = runCondition.runAndReturnResult();
        if (continueResult.getType() != DataTypes.Boolean) {
            throw new Exception("Loop condition must be a boolean");
        }
        return (Boolean) continueResult.getData();
    }

    private DataReturnPacket runInteriorBlock() throws Exception {
        if (interiorCodeBlock != null) {
            return interiorCodeBlock.run();
        }
        return new DataReturnPacket();
    }

    private void executeIncrement() throws Exception {
        if (increment != null) {
            increment.runAndReturnResult();
        }
    }
}
