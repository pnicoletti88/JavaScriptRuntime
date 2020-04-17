package com.Util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class StringHelpersTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void characterCountCorrect(){
        String input = "apples are lovely";
        int count = 3;

        int result = StringHelpers.characterCount(input, 'l');
        assertEquals(count, result);
    }

    @Test
    public void bracketIndexValid() throws Exception{
        String input = "hi my {fgb{}dfbvdfs{df}bf}jkhk";
        int[] count = new int[]{6, 25};

        int[] result = StringHelpers.findFirstAndLastBracketIndex(input, '{','}');
        assertArrayEquals(count, result);
    }

    @Test
    public void bracketWithQuotes() throws Exception{
        String input = "{\"}\"}";
        int[] count = new int[]{0, 4};

        int[] result = StringHelpers.findFirstAndLastBracketIndex(input, '{','}');
        assertArrayEquals(count, result);
    }

    @Test
    public void bracketIndexNotValid() throws Exception{
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("Brackets are poorly formatted");

        String input = "hi my {fgb{}dfbvdfs{df}bfjkhk";

        int[] result = StringHelpers.findFirstAndLastBracketIndex(input, '{','}');
    }

    @Test
    public void quoteRespectingSplitValid() throws Exception{
        String input = "hi, my name \"is \"Jeef";
        String[] expected = new String[]{"hi,", "my", "name", "\"is \"Jeef"};

        List<String> result = StringHelpers.quoteRespectingSplit(input, ' ');

        assertArrayEquals(expected, result.toArray());
    }

    @Test
    public void equalsCount(){
        String input = "let apple = hello == bye";
        Integer[] expected = new Integer[]{10};

        List<Integer> result = StringHelpers.singleEqualsIndexes(input);

        assertArrayEquals(expected, result.toArray());
    }

    @Test
    public void equalsCountQuotes(){
        String input = "let apple = \"hello = bye\"";
        Integer[] expected = new Integer[]{10};

        List<Integer> result = StringHelpers.singleEqualsIndexes(input);

        assertArrayEquals(expected, result.toArray());
    }

    @Test
    public void parsesBlockBody() throws Exception{
        String code = "" +
                "for(let i=0; i<5; i = i + 1){" +
                "let x = 0;" +
                " }";
        String parsedBody = "let x = 0;";

        assertEquals(StringHelpers.parseBlockBody(code), parsedBody);
    }

}
