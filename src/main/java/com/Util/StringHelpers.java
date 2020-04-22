package com.Util;

import com.Exceptions.InternalErrorCodes;
import com.Exceptions.InternalException;

import java.util.ArrayList;
import java.util.List;

public class StringHelpers {
    public static int characterCount(String s, char charToCount) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == charToCount) {
                count++;
            }
        }
        return count;
    }

    public static int[] findFirstAndLastBracketIndex(
            String str, char openBracket, char closeBracket, int startIndex
    ) throws Exception {

        int firstIndex = -1;
        int endIndex = -1;
        int openCount = 0;
        boolean opened = false;
        boolean isInQuotes = false;
        int index = startIndex;

        while ((!opened || openCount > 0) && index < str.length()) {
            char curr = str.charAt(index);
            if (curr == '"') {
                isInQuotes = !isInQuotes;
            }

            if (!isInQuotes) {
                if (curr == openBracket) {
                    if (!opened) {
                        firstIndex = index;
                    }
                    opened = true;
                    openCount += 1;
                } else if (curr == closeBracket) {
                    openCount -= 1;
                    if (openCount == 0) {
                        endIndex = index;
                    }
                }
            }
            index += 1;
        }
        if (firstIndex == -1 || endIndex == -1) {
            throw new InternalException(InternalErrorCodes.STRING_PARSING_FAILURE);
        }
        return new int[]{firstIndex, endIndex};
    }

    public static int[] findFirstAndLastBracketIndex(String str, char openBracket, char closeBracket) throws Exception {
        return findFirstAndLastBracketIndex(str, openBracket, closeBracket, 0);
    }

    public static List<String> quoteRespectingSplit(String str, char splitOn) {
        List<String> output = new ArrayList<>();
        boolean isInQuotes = false;
        int prev = 0;
        for (int i = 0; i < str.length(); i++) {
            char curr = str.charAt(i);
            if (curr == '"') {
                isInQuotes = !isInQuotes;
            }
            if (!isInQuotes && curr == splitOn) {
                output.add(str.substring(prev, i));
                prev = i + 1;
            }
        }
        output.add(str.substring(prev));
        return output;
    }

    public static String joinString(Iterable<String> list) {
        StringBuilder output = new StringBuilder();
        for (String s : list) {
            output.append(s).append("\n");
        }
        return output.toString();
    }

    public static String removeNewLines(String str) {
        StringBuilder output = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (c != '\r' && c != '\n') {
                output.append(c);
            }
        }
        return output.toString();
    }

    public static List<Integer> singleEqualsIndexes(String str) {
        ArrayList<Integer> output = new ArrayList<>();
        boolean isInQuotes = false;
        for (int i = 0; i < str.length(); i++) {
            char curr = str.charAt(i);
            if (curr == '"') {
                isInQuotes = !isInQuotes;
            }

            if (!isInQuotes && curr == '=') {
                if (i + 1 == str.length() || str.charAt(i + 1) != '=') {
                    output.add(i);
                } else {
                    i += 1;
                }
            }
        }
        return output;
    }

    public static String parseBlockBody(String code, int startIndex) throws Exception {
        int[] indexes = findFirstAndLastBracketIndex(code, '{', '}', startIndex);
        return code.substring(indexes[0] + 1, indexes[1]).trim();
    }

    public static String parseBlockBody(String code) throws Exception {
        return parseBlockBody(code, 0);
    }

    public static boolean isFirstWordInString(String pattern, String code, int startIndex){
        StringBuilder seen = new StringBuilder();
        int index = startIndex;
        while(seen.length() < pattern.length() && index < code.length()){
            if(seen.length() != 0 || code.charAt(index) != ' '){
                seen.append(code.charAt(index));
            }
            index++;
        }
        return seen.toString().equals(pattern);
    }

    public static String findQuotedString(String s, int startIndex) throws Exception{
        int firstIndex = s.indexOf('"', startIndex);
        int endIndex = s.indexOf('"', firstIndex+1);
        if(endIndex == -1){
            throw new InternalException(InternalErrorCodes.STRING_PARSING_FAILURE);
        }
        return s.substring(firstIndex, endIndex + 1);
    }
}

