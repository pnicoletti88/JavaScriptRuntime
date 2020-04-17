package com.SpecialBlock;

import com.Scopes.Scope;
import com.SpecialBlock.Loops.ForLoop;
import com.SpecialBlock.Loops.WhileLoop;

import java.util.Arrays;
import java.util.HashSet;

public class SpecialBlockRunnerFactory {
    private static final String[] specialBlockRunnerPatterns = {"for ", "for(", "while ", "while("};
    private static final int[] patternLengths = {4, 6};
    private static final HashSet<String> specialBlockRunnerPatternsMap = new HashSet<>(Arrays.asList(specialBlockRunnerPatterns));

    public boolean isStringStartSpecialBlockRunnerType(String s) {
        return !findPatternMatch(s).equals("");
    }

    private String findPatternMatch(String s){
        for (int len : patternLengths) {
            String strOfLen = s.length() >= len ? s.substring(0, len) : "";
            if (specialBlockRunnerPatternsMap.contains(strOfLen)) {
                return strOfLen;
            }
        }
        return "";
    }

    public SpecialBlockRunner createSpecialBlockRunner(String type, String codeBlock, Scope scope) throws Exception {
        for (int len : patternLengths) {
            switch (findPatternMatch(type)) {
                case "for(":
                case "for ":
                    return new ForLoop(codeBlock, scope);
                case "while(":
                case "while ":
                    return new WhileLoop(codeBlock, scope);
                default:
                    throw new Exception("Internal Error");
            }
        }
        throw new Exception("Internal Error");
    }
}
