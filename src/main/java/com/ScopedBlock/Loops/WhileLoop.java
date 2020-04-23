package com.ScopedBlock.Loops;

import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.SingleLineHandlers.CodeLine;
import com.Scopes.Scope;
import com.Util.StringHelpers;
import com.Process;

public class WhileLoop extends Loop {
    private String loopCondition;

    public WhileLoop(String code, Scope scope, Process process) throws Exception{
        super(code, scope, "while", process);
        loopCondition = getLoopInstructions();
        validateLoopCondition();
        activateLoopCondition();
    }

    public void validateLoopCondition() throws Exception{
        if(loopCondition.trim().equals("") || StringHelpers.characterCount(loopCondition, ';') > 0){
            throw new ExternalException(ExternalErrorCodes.LOOP_ERROR, loopCondition);
        }
    }

    public void activateLoopCondition(){
        CodeLine loopCond = new CodeLine(loopCondition, super.getScope(), super.getProcess());
        super.setRunCondition(loopCond);
    }
}
