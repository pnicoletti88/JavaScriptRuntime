package com.ScopedBlock.Loops;

import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.SingleLineHandlers.CodeLine;
import com.Scopes.Scope;
import com.Util.StringHelpers;
import com.Process;

import java.util.List;

public class ForLoop extends Loop {
    private List<String> parsedLoopCommands;


    public ForLoop(String code, Scope scope, Process process) throws Exception {
        super(code, scope, "for", process);
        parseLoopConditions();
        activateLoopCommands();
    }

    public List<String> getParsedLoopCommands() {
        return parsedLoopCommands;
    }

    private void parseLoopConditions() throws Exception {
        String loopInstructions = getLoopInstructions();
        if(StringHelpers.characterCount(loopInstructions, ';') != 2){
            throw new ExternalException(ExternalErrorCodes.LOOP_ERROR, loopInstructions);
        }
        parsedLoopCommands = StringHelpers.quoteRespectingSplit(loopInstructions, ';');
    }

    private void activateLoopCommands() throws Exception {
        performSetUpOperation(parsedLoopCommands.get(0));

        CodeLine continueCondition;
        if(isNotBlank(parsedLoopCommands.get(1))) {
            continueCondition = new CodeLine(parsedLoopCommands.get(1), super.getScope(), super.getProcess());
        } else {
            continueCondition = new CodeLine("True", super.getScope(), super.getProcess());
        }
        super.setRunCondition(continueCondition);

        if(isNotBlank(parsedLoopCommands.get(2))){
            CodeLine incrementCondition = new CodeLine(parsedLoopCommands.get(2), super.getScope(), super.getProcess());
            super.setIncrement(incrementCondition);
        }
    }

    private boolean isNotBlank(String s){
        return !s.trim().equals("");
    }

    private void performSetUpOperation(String op) throws Exception {
        if (isNotBlank(op)) {
            (new CodeLine(op, super.getScope(), super.getProcess())).runAndReturnResult();
        }
        getScope().loopDataInsertComplete();
    }
}
