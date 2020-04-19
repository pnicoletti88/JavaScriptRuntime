package com.Processes;

import com.Process;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpressionTesting {

    @Test
    public void expressionInsideOfFunctionCall() throws Exception {
        String expectedState = "fun-" +
                "function fun(a){" +
                "   return a;" +
                "}" +
                "-Function\n" +
                "x-4.0-Number\n";
        String code = "" +
                "function fun(a){" +
                "   return a;" +
                "}" +
                "let x = fun(5 - 1);";

        Process exec = new Process(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }
}
