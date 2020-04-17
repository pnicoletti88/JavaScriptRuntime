package com.ScopedBlock;

import com.Scopes.Scope;
import com.ScopedBlock.Loops.ForLoop;
import com.ScopedBlock.Loops.WhileLoop;
import com.Util.StringHelpers;

import java.util.HashMap;

public class ScopedBlockRunnerFactory {
    private int maxPatternLength = 0;
    private HashMap<String, ScopedBlockRunnerTypes> patternToTypeMap = new HashMap<>();

    public enum ScopedBlockRunnerTypes {
        ForLoop,
        WhileLoop,
        ConditionalBlock
    }

    public ScopedBlockRunnerFactory(){
        patternToTypeMap.put("for", ScopedBlockRunnerTypes.ForLoop);
        patternToTypeMap.put("while", ScopedBlockRunnerTypes.WhileLoop);
        patternToTypeMap.put("if", ScopedBlockRunnerTypes.ConditionalBlock);

        for(String key:patternToTypeMap.keySet()){
            maxPatternLength = Math.max(maxPatternLength, key.length());
        }
    }

    public boolean isSpecialBlockRunnerType(String s) {
        boolean validLength = s != null && s.length()-1 <= maxPatternLength && s.length() > 0;
        if(!validLength){
            return false;
        }

        char lastChar = s.charAt(s.length()-1);
        boolean validLastChar = lastChar == ' ' || lastChar == '(';
        if(validLastChar){
            String lastCharRemoved = s.substring(0, s.length()-1);
            return patternToTypeMap.containsKey(lastCharRemoved);
        }
        return false;
    }

    public ScopedBlockRunnerTypes getType(String s) throws Exception{
        if(isSpecialBlockRunnerType(s)){
            return patternToTypeMap.get(s.substring(0, s.length()-1));
        }
        throw new Exception("Cannot get type");
    }

    public String parseBlock(ScopedBlockRunnerTypes type, String code, int startIndex) throws Exception{
        switch(type){
            case ForLoop:
            case WhileLoop:
                return parseStandardBlock(code, startIndex);
            case ConditionalBlock:
                //funky parse
        }
        return "";
    }

    private String parseStandardBlock(String code, int startIndex) throws Exception{
        int blockEndIndex = StringHelpers.findFirstAndLastBracketIndex(code, '{', '}', startIndex)[1];
        return code.substring(startIndex, blockEndIndex + 1).trim();
    }

    public ScopedBlockRunner createSpecialBlockRunner(ScopedBlockRunnerTypes type, String codeBlock, Scope scope) throws Exception {
        switch (type) {
            case ForLoop:
                return new ForLoop(codeBlock, scope);
            case WhileLoop:
                return new WhileLoop(codeBlock, scope);
            case ConditionalBlock:
                return null;
        }
        //temp
        return null;
    }
}
