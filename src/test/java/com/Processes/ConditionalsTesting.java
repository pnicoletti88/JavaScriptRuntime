package com.Processes;

import com.Process;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConditionalsTesting {
    @Test
    public void runsIfStatementCorrectly() throws Exception {
        String expectedState = "" +
                "j-3.0-Number\n" +
                "x-10.0-Number\n" +
                "y-8.0-Number\n";
        String code = "" +
                "let x=10;" +
                "let y=8;" +
                "let j=0;" +
                "if(x<y){" +
                "j=1;" +
                "} else if(x==y){" +
                "j=2;" +
                "} else if(x>y){" +
                "j=3;" +
                "}";

        Process exec = new Process(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }

    @Test
    public void runsNestedIfStatement() throws Exception {
        String expectedState = "" +
                "j-2.0-Number\n" +
                "x-10.0-Number\n" +
                "y-8.0-Number\n";
        String code = "" +
                "let x=10;" +
                "let y=8;" +
                "let j=0;" +
                "if(x>y){" +
                "if(x<y){" +
                "j=1;" +
                "} else {" +
                "j=2;" +
                "} " +
                "} else {" +
                "j=3;" +
                "}";

        Process exec = new Process(code);
        exec.start();
        String state = exec.serializeState();
        assertEquals(expectedState, state);
    }
}
