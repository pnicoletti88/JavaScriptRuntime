package com.Executions;

import com.Execution;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionTesting {
    @Test
    public void functionDeclaresAndRunsNoParams() throws Exception {
        String expectedState = "" +
                "fun-function fun(){let x=1;}-Function\n";
        String code = "" +
                "function fun(){let x=1;}" +
                "fun();";

        Execution exec = new Execution(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }

    @Test
    public void functionDeclaresAndRunsWithParams() throws Exception {
        String expectedState = "" +
                "fun-function fun(a,b){let x=1;}-Function\n";
        String code = "" +
                "function fun(a,b){let x=1;}" +
                "fun(1,2);";

        Execution exec = new Execution(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }

    @Test
    public void functionModifiesParentState() throws Exception {
        String expectedState = "" +
                "fun-function fun(a,b){ x = x + a + b; }-Function\n" +
                "x-4.0-Number\n";
        String code = "" +
                "let x = 1;" +
                "function fun(a,b){ x = x + a + b; }" +
                "fun(1,2);";

        Execution exec = new Execution(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }

    @Test
    public void functionReturnsValue() throws Exception {
        String expectedState = "" +
                "fun-function fun(a,b){ return x + a + b; }-Function\n" +
                "x-4.0-Number\n";
        String code = "" +
                "let x = 1;" +
                "function fun(a,b){ return x + a + b; }" +
                "x = fun(1,2);";

        Execution exec = new Execution(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }
}
