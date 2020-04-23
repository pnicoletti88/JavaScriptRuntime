package com.Processes.BuiltInFunctions;

import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.Process;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ToStringTesting {
    @Test
    public void doesToStringExistInScope() throws Exception{
        String code = "let toString = 1;";
        try {
            Process exec = new Process(code);
            exec.start();
        } catch(ExternalException e) {
            assertEquals(ExternalErrorCodes.VARIABLE_REDECLARATION, e.getCode());
        }
    }

    @Test
    public void toStringConvertsInt() throws Exception{
        String code = "let i = toString(1);";
        String expectedState = "i-1.0-String\n";
        Process exec = new Process(code);
        exec.start();
        assertEquals(expectedState, exec.serializeState());
    }

    @Test
    public void toStringConvertsBoolean() throws Exception{
        String code = "let i = toString(True);";
        String expectedState = "i-true-String\n";
        Process exec = new Process(code);
        exec.start();
        assertEquals(expectedState, exec.serializeState());
    }

    @Test
    public void toStringConvertsFunction() throws Exception{
        String code = "" +
                "function hi(a,b){}" +
                "let i = toString(hi);";
        String expectedState = "hi-function hi(a,b){}-Function\n" +
                "i-function hi(a,b){}-String\n";
        Process exec = new Process(code);
        exec.start();
        assertEquals(expectedState, exec.serializeState());
    }
}
