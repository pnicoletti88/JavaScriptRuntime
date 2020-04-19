package com.Processes;

import com.Process;
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

        Process exec = new Process(code);
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

        Process exec = new Process(code);
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

        Process exec = new Process(code);
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

        Process exec = new Process(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }

    @Test
    public void recursionWorks() throws Exception {
        String expectedState = "" +
                "fun-" +
                "function fun(a){" +
                "   if(a > 0){" +
                "       let temp=a-1;" +
                "       return fun(temp) + 1;" +
                "   }" +
                "   return 8;" +
                "}" +
                "-Function\n" +
                "x-13.0-Number\n";
        String code = "" +
                "function fun(a){" +
                "   if(a > 0){" +
                "       let temp=a-1;" +
                "       return fun(temp) + 1;" +
                "   }" +
                "   return 8;" +
                "}" +
                "let x = fun(5);";

        Process exec = new Process(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }
}
