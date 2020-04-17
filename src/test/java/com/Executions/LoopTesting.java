package com.Executions;

import com.Execution;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoopTesting {
    @Test
    public void varibleInUpperScope() throws Exception {
        String expectedState = "" +
                "i-10.0-Number\n";
        String code = "" +
                "let i=0;" +
                "for(let j=0; j<1;j = j + 1){" +
                "i=10;" +
                "}";

        Execution exec = new Execution(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }

    @Test
    public void forLoopRunsCorrectly() throws Exception {
        String expectedState = "" +
                "i-9.0-Number\n" +
                "p-10.0-Number\n";
        String code = "" +
                "let i=0;" +
                "let p = 10;" +
                "for(let j=0; j<p;j = j + 1){" +
                "i=j;" +
                "}";

        Execution exec = new Execution(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }

    @Test
    public void doubleForLoopRunsCorrectly() throws Exception {
        String expectedState = "" +
                "i-25.0-Number\n";
        String code = "" +
                "let i=0;" +
                "for(let j=0; j<5; j = j + 1){" +
                "for(let p=0; p<5; p = p + 1){" +
                "i = i + 1;" +
                "}" +
                "}";

        Execution exec = new Execution(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }

    @Test
    public void returnFromLoopWorks() throws Exception {
        String expectedState = "" +
                "func-function func(){for(let i=0; i<5; i = i + 1){return i;}return 5;}-Function\n" +
                "i-0.0-Number\n";
        String code = "" +
                "function func(){" +
                "for(let i=0; i<5; i = i + 1){" +
                "return i;" +
                "}" +
                "return 5;" +
                "}" +
                "" +
                "let i=func();";

        Execution exec = new Execution(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }
}
