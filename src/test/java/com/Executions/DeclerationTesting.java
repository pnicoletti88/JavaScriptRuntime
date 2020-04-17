package com.Executions;

import com.Execution;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class DeclerationTesting {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void singleIntegerAllocation() throws Exception {
        String expectedState = "i-10.0-Number\n";
        String code = "let i=10;";

        Execution exec = new Execution(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }

    @Test
    public void multipleIntegerAllocation() throws Exception {
        String expectedState = "" +
                "f-4.0-Number\n" +
                "i-10.0-Number\n" +
                "j-14.0-Number\n";
        String code = "let i=10; let j=14; let f=4;";

        Execution exec = new Execution(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }

    @Test
    public void integersPassByValue() throws Exception {
        String expectedState = "" +
                "i-10.0-Number\n" +
                "j-5.0-Number\n";
        String code = "let i=10; let j=i; j=5;";

        Execution exec = new Execution(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }

    @Test
    public void singleStringAllocation() throws Exception {
        String expectedState = "i-apples-String\n";
        String code = "let i=\"apples\";";

        Execution exec = new Execution(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }

    @Test
    public void multipleStringAllocation() throws Exception {
        String expectedState = "" +
                "f-wee-String\n" +
                "i-hi-String\n" +
                "j-bye-String\n";
        String code = "let i=\"hi\"; let j=\"bye\"; let f=\"wee\";";

        Execution exec = new Execution(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }

    @Test
    public void stringPassByValue() throws Exception {
        String expectedState = "" +
                "i-hi-String\n" +
                "j-wee-String\n";
        String code = "let i=\"hi\"; let j=i; j=\"wee\";";

        Execution exec = new Execution(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }

    @Test
    public void numberedVariableName() throws Exception {
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("Invalid Number: 1a");

        String code = "let i=1a;";

        Execution exec = new Execution(code);
        exec.start();
    }
}
