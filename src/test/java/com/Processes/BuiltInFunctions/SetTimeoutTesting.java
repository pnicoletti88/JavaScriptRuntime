package com.Processes.BuiltInFunctions;

import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.Process;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SetTimeoutTesting {
    @Test
    public void doesToSetTimeoutExistInScope() throws Exception{
        String code = "let setTimeOut = 1;";
        try {
            Process exec = new Process(code);
            exec.start();
        } catch(ExternalException e) {
            assertEquals(ExternalErrorCodes.VARIABLE_REDECLARATION, e.getCode());
        }
    }

    @Test
    public void canCallSetTimeOut() throws Exception{
        String code = "" +
                "let i=0;" +
                "function callback(){i=2;}" +
                "setTimeOut(callback, 10);" +
                "i=1;";
        String expectedState = "callback-function callback(){i=2;}-Function\n" +
                "i-2.0-Number\n";

        Process exec = new Process(code);
        exec.start();

        assertEquals(expectedState,exec.serializeState());
    }

    @Test
    public void canCallMultipleSetTimeOut() throws Exception{
        String code = "" +
                "let i=0;" +
                "function callback(){i=i+1;}" +
                "setTimeOut(callback, 10);" +
                "setTimeOut(callback, 10);" +
                "setTimeOut(callback, 10);" +
                "setTimeOut(callback, 10);" +
                "setTimeOut(callback, 10);" +
                "i=1;";
        String expectedState = "callback-function callback(){i=i+1;}-Function\n" +
                "i-6.0-Number\n";

        Process exec = new Process(code);
        exec.start();

        assertEquals(expectedState,exec.serializeState());
    }

    @Test
    public void canNestSetTimeOut() throws Exception{
        String code = "" +
                "let i=5;" +
                "function callback(){if(i > 0){setTimeOut(callback, 10); i = i - 1;}}" +
                "setTimeOut(callback, 10);";
        String expectedState = "callback-function callback(){if(i > 0){setTimeOut(callback, 10); i = i - 1;}}-Function\n" +
                "i-0.0-Number\n";

        Process exec = new Process(code);
        exec.start();

        assertEquals(expectedState,exec.serializeState());
    }
}
