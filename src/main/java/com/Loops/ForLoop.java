package com.Loops;

import com.CodeLine;
import com.Executor;
import com.Scopes.Scope;
import com.Util.StringHelpers;

import java.util.List;

public class ForLoop extends Loop {
    public ForLoop(String code, Scope scope) throws Exception{
        super(code, scope);
        setParsedLoopCommands(parseLoopConditions(code));
        validateLoopCommands();
        activateLoopCommands();
        setLoopBody(parseLoopBody(code));
        activateLoopBody();
    }

    private List<String> parseLoopConditions(String code) throws Exception {
        if (!code.substring(0, 3).equals("for")) {
            throw new Exception("Internal loop error");
        }
        int[] indexes = StringHelpers.findFirstAndLastBracketIndex(code, '(', ')');
        String loopInstructions = code.substring(indexes[0] + 1, indexes[1]);
        return StringHelpers.quoteRespectingSplit(loopInstructions, ';');
    }

    private void validateLoopCommands(){
        //TODO
    }

    private void activateLoopCommands() throws Exception {
        setUpOperation(getParsedLoopCommands().get(0));

        CodeLine incrementCondition = new CodeLine(getParsedLoopCommands().get(2), getScope());
        setIncrement(incrementCondition);

        CodeLine continueCondition = new CodeLine(getParsedLoopCommands().get(1), getScope());
        setRunCondition(continueCondition);
    }

    private void setUpOperation(String op) throws Exception {
        if (!op.trim().equals("")) {
            (new CodeLine(op, getScope())).runAndReturnResult();
        }
        getScope().loopDataInsertComplete();
    }

    private String parseLoopBody(String code) throws Exception {
        int[] indexes = StringHelpers.findFirstAndLastBracketIndex(code, '{', '}');
        return code.substring(indexes[0] + 1, indexes[1]);
    }

    private void activateLoopBody() throws Exception{
        Executor interiorCodeBlock = new Executor(getLoopBody(), getScope());
        setInteriorCodeBlock(interiorCodeBlock);
    }

}
