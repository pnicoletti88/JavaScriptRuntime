package com.ScopedBlock.Loops;

import com.SingleLineHandlers.CodeLine;
import com.Scopes.Scope;
import com.Util.StringHelpers;

public class WhileLoop extends Loop {
    private String loopCondition;

    public WhileLoop(String code, Scope scope) throws Exception{
        super(code, scope, "while");
        loopCondition = parseLoopCondition();
        validateLoopCondition();
        activateLoopCondition();
    }

    public String parseLoopCondition() throws Exception{
        int[] indices = StringHelpers.findFirstAndLastBracketIndex(getCode(), '(', ')');
        return getCode().substring(indices[0] + 1, indices[1]).trim();
    }

    public void validateLoopCondition() throws Exception{
        if(loopCondition.trim().equals("") || StringHelpers.characterCount(loopCondition, ';') > 0){
            throw new Exception("Illegal Loop Format");
        }
    }

    public void activateLoopCondition(){
        CodeLine loopCond = new CodeLine(loopCondition, super.getScope());
        super.setRunCondition(loopCond);
    }
}
