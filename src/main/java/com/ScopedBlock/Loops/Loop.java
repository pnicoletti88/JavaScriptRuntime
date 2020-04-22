package com.ScopedBlock.Loops;

import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.Exceptions.InternalErrorCodes;
import com.Exceptions.InternalException;
import com.SingleLineHandlers.CodeLine;
import com.Data.Data;
import com.Data.DataReturnPacket;
import com.Data.DataTypes;
import com.Executor;
import com.Scopes.LoopScope;
import com.Scopes.Scope;
import com.ScopedBlock.ScopedBlockRunner;
import com.Util.StringHelpers;

public abstract class Loop implements ScopedBlockRunner {
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
            throw new InternalException(InternalErrorCodes.INVALID_LOOP_CALL);
        }
        for(int i=loopDeclarationName.length();i<code.length();i++){
            if(code.charAt(i) == '('){
                return;
            } else if(code.charAt(i) != ' '){
                String expression = code.substring(0, i+1);
                throw new ExternalException(ExternalErrorCodes.LOOP_ERROR, expression);
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

    public String getLoopInstructions() throws Exception{
        try {
            int[] indexes = StringHelpers.findFirstAndLastBracketIndex(getCode(), '(', ')');
            return getCode().substring(indexes[0] + 1, indexes[1]).trim();
        } catch (InternalException e){
            throw new InternalException(InternalErrorCodes.INVALID_LOOP_CALL);
        }

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
            throw new ExternalException(ExternalErrorCodes.TYPE_ERROR, "loop needs a boolean condition, received " + continueResult.getType().toString());
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
