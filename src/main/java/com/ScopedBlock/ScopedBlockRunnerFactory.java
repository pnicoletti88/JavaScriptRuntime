package com.ScopedBlock;

import com.Exceptions.InternalErrorCodes;
import com.Exceptions.InternalException;
import com.ScopedBlock.Conditionals.IfStatement;
import com.Scopes.Scope;
import com.ScopedBlock.Loops.ForLoop;
import com.ScopedBlock.Loops.WhileLoop;
import com.Process;

import java.util.HashMap;

import static com.Util.StringHelpers.*;

public class ScopedBlockRunnerFactory {
    private int maxPatternLength = 0;
    private HashMap<String, ScopedBlockRunnerTypes> patternToTypeMap = new HashMap<>();

    public enum ScopedBlockRunnerTypes {
        ForLoop,
        WhileLoop,
        ConditionalBlock
    }

    public ScopedBlockRunnerFactory() {
        patternToTypeMap.put("for", ScopedBlockRunnerTypes.ForLoop);
        patternToTypeMap.put("while", ScopedBlockRunnerTypes.WhileLoop);
        patternToTypeMap.put("if", ScopedBlockRunnerTypes.ConditionalBlock);

        for (String key : patternToTypeMap.keySet()) {
            maxPatternLength = Math.max(maxPatternLength, key.length());
        }
    }

    public boolean isSpecialBlockRunnerType(String s) {
        boolean validLength = s != null && s.length() - 1 <= maxPatternLength && s.length() > 0;
        if (!validLength) {
            return false;
        }

        char lastChar = s.charAt(s.length() - 1);
        boolean validLastChar = lastChar == ' ' || lastChar == '(';
        if (validLastChar) {
            String lastCharRemoved = s.substring(0, s.length() - 1);
            return patternToTypeMap.containsKey(lastCharRemoved);
        }
        return false;
    }

    public ScopedBlockRunnerTypes getType(String s) throws Exception {
        if (isSpecialBlockRunnerType(s)) {
            return patternToTypeMap.get(s.substring(0, s.length() - 1));
        }
        throw new InternalException(InternalErrorCodes.UNKNOWN_BLOCK_TYPE);
    }

    public String parseBlock(ScopedBlockRunnerTypes type, String code, int startIndex) throws Exception {
        try {
            switch (type) {
                case ForLoop:
                case WhileLoop:
                    return parseStandardBlock(code, startIndex);
                case ConditionalBlock:
                    return parseConditionalBlock(code, startIndex);
            }
        } catch (InternalException e) {
            throw new InternalException(InternalErrorCodes.INVALID_BLOCK_CALL);
        }
        throw new InternalException(InternalErrorCodes.UNKNOWN_BLOCK_TYPE);
    }

    private String parseStandardBlock(String code, int startIndex) throws Exception {
        int blockEndIndex = findFirstAndLastBracketIndex(code, '{', '}', startIndex)[1];
        return code.substring(startIndex, blockEndIndex + 1).trim();
    }

    private String parseConditionalBlock(String code, int startIndex) throws Exception {

        int currentBlockEndIndex = findFirstAndLastBracketIndex(code, '{', '}', startIndex)[1];
        while (IfStatement.isElseIfStatement(code, currentBlockEndIndex + 1)) {
            currentBlockEndIndex = findFirstAndLastBracketIndex(code, '{', '}', currentBlockEndIndex + 1)[1];
        }
        if (IfStatement.isElseStatement(code, currentBlockEndIndex + 1)) {
            currentBlockEndIndex = findFirstAndLastBracketIndex(code, '{', '}', currentBlockEndIndex + 1)[1];
        }
        return code.substring(startIndex, currentBlockEndIndex + 1).trim();

    }

    public ScopedBlockRunner createSpecialBlockRunner(ScopedBlockRunnerTypes type, String codeBlock, Scope scope, Process process) throws Exception {
        switch (type) {
            case ForLoop:
                return new ForLoop(codeBlock, scope, process);
            case WhileLoop:
                return new WhileLoop(codeBlock, scope, process);
            case ConditionalBlock:
                return new IfStatement(codeBlock, scope, process);
        }
        throw new InternalException(InternalErrorCodes.UNKNOWN_BLOCK_TYPE);
    }
}
