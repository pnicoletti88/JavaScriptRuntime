package com.ScopedBlock.Loops;

import com.CodeLine;
import com.Scopes.Scope;
import com.Util.StringHelpers;

import java.util.List;

public class ForLoop extends Loop {
    private List<String> parsedLoopCommands;


    public ForLoop(String code, Scope scope) throws Exception {
        super(code, scope, "for");
        parseLoopConditions();
        activateLoopCommands();
    }

    public List<String> getParsedLoopCommands() {
        return parsedLoopCommands;
    }

    private void parseLoopConditions() throws Exception {
        int[] indexes = StringHelpers.findFirstAndLastBracketIndex(super.getCode(), '(', ')');
        String loopInstructions = super.getCode().substring(indexes[0] + 1, indexes[1]).trim();
        if(StringHelpers.characterCount(loopInstructions, ';') != 2){
            throw new Exception("Invalid loop syntax");
        }
        parsedLoopCommands = StringHelpers.quoteRespectingSplit(loopInstructions, ';');
    }

    private void activateLoopCommands() throws Exception {
        performSetUpOperation(parsedLoopCommands.get(0));

        CodeLine continueCondition;
        if(isNotBlank(parsedLoopCommands.get(1))) {
            continueCondition = new CodeLine(parsedLoopCommands.get(1), super.getScope());
        } else {
            continueCondition = new CodeLine("True", super.getScope());
        }
        super.setRunCondition(continueCondition);

        if(isNotBlank(parsedLoopCommands.get(2))){
            CodeLine incrementCondition = new CodeLine(parsedLoopCommands.get(2), super.getScope());
            super.setIncrement(incrementCondition);
        }
    }

    private boolean isNotBlank(String s){
        return !s.trim().equals("");
    }

    private void performSetUpOperation(String op) throws Exception {
        if (isNotBlank(op)) {
            (new CodeLine(op, super.getScope())).runAndReturnResult();
        }
        getScope().loopDataInsertComplete();
    }
}
