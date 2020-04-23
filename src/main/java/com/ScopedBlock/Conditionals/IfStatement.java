package com.ScopedBlock.Conditionals;

import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.Exceptions.InternalErrorCodes;
import com.Exceptions.InternalException;
import com.SingleLineHandlers.CodeLine;
import com.Data.Data;
import com.Data.DataReturnPacket;
import com.Data.DataTypes;
import com.Executor;
import com.ScopedBlock.ScopedBlockRunner;
import com.Scopes.Scope;
import com.Scopes.StandardScope;
import com.Util.StringHelpers;
import com.Process;

import static com.Util.StringHelpers.isFirstWordInString;

public class IfStatement implements ScopedBlockRunner {
    private String code;
    private Scope scope;
    private int codeIndex = 0;
    private Process process;

    public IfStatement(String code, Scope parentScope, Process process) {
        this.code = code;
        this.process = process;
        scope = new StandardScope(parentScope);
    }

    public static boolean isIfStatement(String code, int startIndex) {
        boolean isElseIf = isFirstWordInString("if ", code, startIndex);
        isElseIf = isElseIf || isFirstWordInString("if(", code, startIndex);
        return isElseIf;
    }

    public static boolean isElseIfStatement(String code, int startIndex) {
        boolean isElseIf = isFirstWordInString("else if ", code, startIndex);
        isElseIf = isElseIf || isFirstWordInString("else if(", code, startIndex);
        return isElseIf;
    }

    public static boolean isElseStatement(String code, int startIndex) {
        boolean isElse = isFirstWordInString("else ", code, startIndex);
        isElse = isElse || isFirstWordInString("else{", code, startIndex);
        return isElse;
    }

    public Scope getScope() {
        return scope;
    }

    public DataReturnPacket run() throws Exception {
        String codeToRun = findCodeBlockToRun();
        if (codeToRun != null) {
            return (new Executor(codeToRun, scope, process)).run();
        }
        return new DataReturnPacket();
    }

    private String findCodeBlockToRun() throws Exception {
        while (codeIndex < code.length()) {
            if (shouldCurrentBlockExecute()) {
                try {
                    return StringHelpers.parseBlockBody(code, codeIndex);
                } catch(InternalException e){
                    throw new InternalException(InternalErrorCodes.POORLY_FORMATTED_CONDITIONAL);
                }
            } else {
                incrementCodeIndexToNextBlock();
            }
        }
        return null;
    }

    private boolean shouldCurrentBlockExecute() throws Exception {
        boolean executeBlock = false;
        if (isElseIfStatement(code, codeIndex) || isIfStatement(code, codeIndex)) {
            executeBlock = parseAndEvaluateCondition();
        } else if (isElseStatement(code, codeIndex)) {
            executeBlock = true;
        } else {
            throw new InternalException(InternalErrorCodes.UNREACHABLE_CODE);
        }
        return executeBlock;
    }

    private void incrementCodeIndexToNextBlock() throws Exception {
        try {
            int blockEndIndex = StringHelpers.findFirstAndLastBracketIndex(code, '{', '}', codeIndex)[1];
            codeIndex = blockEndIndex + 1;
        } catch (InternalException e) {
            throw new InternalException(InternalErrorCodes.POORLY_FORMATTED_CONDITIONAL);
        }
    }

    private boolean parseAndEvaluateCondition() throws Exception {
        int[] bracketIndex;
        try {
            bracketIndex = StringHelpers.findFirstAndLastBracketIndex(code, '(', ')', codeIndex);
        } catch (Exception e) {
            throw new InternalException(InternalErrorCodes.POORLY_FORMATTED_CONDITIONAL);
        }
        CodeLine expression = new CodeLine(code.substring(bracketIndex[0] + 1, bracketIndex[1]), scope, process);
        Data result = expression.runAndReturnResult();
        return isDataTrue(result);
    }

    private boolean isDataTrue(Data d) throws Exception {
        if (d.getType() != DataTypes.Boolean) {
            String details = "conditional statement expecting boolean type, not: " + d.getType().toString();
            throw new ExternalException(ExternalErrorCodes.TYPE_ERROR, details);
        }
        return (boolean) d.getData();
    }
}
