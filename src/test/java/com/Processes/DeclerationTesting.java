package com.Processes;

import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.Process;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DeclerationTesting {

    @Test
    public void singleIntegerAllocation() throws Exception {
        String expectedState = "i-10.0-Number\n";
        String code = "let i=10;";

        Process exec = new Process(code);
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

        Process exec = new Process(code);
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

        Process exec = new Process(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }

    @Test
    public void singleStringAllocation() throws Exception {
        String expectedState = "i-apples-String\n";
        String code = "let i=\"apples\";";

        Process exec = new Process(code);
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

        Process exec = new Process(code);
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

        Process exec = new Process(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }

    @Test
    public void numberedVariableName() throws Exception {
        String code = "let i=1a;";

        try {
            Process exec = new Process(code);
            exec.start();
            fail();
        } catch(ExternalException e){
            assertEquals(ExternalErrorCodes.UNKNOWN_PRIMITIVE_DATA, e.getCode());
        }
    }

    @Test
    public void semiColonInString() throws Exception{
        String expectedState = "i-hi;-String\n";
        String code = "let i=\"hi;\";";

        Process exec = new Process(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }
}
