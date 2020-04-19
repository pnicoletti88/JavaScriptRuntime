package com.ScopedBlock.Conditionals;

import com.SingleLineHandlers.CodeLine;
import com.Data.Data;
import com.Data.DataReturnPacket;
import com.Data.DataTypes;
import com.Executor;
import com.ScopedBlock.ScopedBlockRunner;
import com.Scopes.Scope;
import com.Scopes.StandardScope;
import com.Util.StringHelpers;

import static com.Util.StringHelpers.isFirstWordInString;

public class IfStatement implements ScopedBlockRunner {
    private String code;
    private Scope scope;
    private int codeIndex = 0;

    public IfStatement(String code, Scope parentScope){
        this.code = code;
        scope = new StandardScope(parentScope);
    }

    public static boolean isIfStatement(String code, int startIndex){
        boolean isElseIf = isFirstWordInString("if ", code, startIndex);
        isElseIf = isElseIf || isFirstWordInString("if(", code, startIndex);
        return isElseIf;
    }

    public static boolean isElseIfStatement(String code, int startIndex){
        boolean isElseIf = isFirstWordInString("else if ", code, startIndex);
        isElseIf = isElseIf || isFirstWordInString("else if(", code, startIndex);
        return isElseIf;
    }

    public static boolean isElseStatement(String code, int startIndex){
        boolean isElse = isFirstWordInString("else ", code, startIndex);
        isElse = isElse || isFirstWordInString("else{", code, startIndex);
        return isElse;
    }

    public Scope getScope() {
        return scope;
    }

    public DataReturnPacket run() throws Exception{
        String codeToRun = findCodeBlockToRun();
        if(codeToRun != null){
            return (new Executor(codeToRun, scope)).run();
        }
        return new DataReturnPacket();
    }

    private String findCodeBlockToRun() throws Exception{
        while(codeIndex < code.length()){
            if(shouldCurrentBlockExecute()){
                return StringHelpers.parseBlockBody(code, codeIndex);
            } else {
                incrementCodeIndexToNextBlock();
            }
        }
        return null;
    }

    private boolean shouldCurrentBlockExecute() throws Exception{
        boolean executeBlock = false;
        if(isElseIfStatement(code, codeIndex) || isIfStatement(code, codeIndex)){
            executeBlock = parseAndEvaluateCondition();
        } else if(isElseStatement(code, codeIndex)){
            executeBlock = true;
        } else {
            throw new Exception("Internal Error");
        }
        return executeBlock;
    }

    private void incrementCodeIndexToNextBlock() throws Exception {
        int blockEndIndex = StringHelpers.findFirstAndLastBracketIndex(code, '{', '}', codeIndex)[1];
        codeIndex = blockEndIndex + 1;
    }

    private boolean parseAndEvaluateCondition() throws Exception{
        int[] bracketIndex = StringHelpers.findFirstAndLastBracketIndex(code, '(', ')', codeIndex);
        CodeLine expression = new CodeLine(code.substring(bracketIndex[0] + 1, bracketIndex[1]), scope);
        Data result = expression.runAndReturnResult();
        return isDataTrue(result);
    }

    private boolean isDataTrue(Data d) throws Exception{
        if(d.getType() != DataTypes.Boolean){
            throw new Exception("If statement data type must be boolean!");
        }
        return (boolean)d.getData();
    }
}
