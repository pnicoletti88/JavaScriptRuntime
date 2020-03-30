package com.Loops;

import com.Loops.Loop;
import com.Scopes.StandardScope;
import org.junit.Test;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class ForLoopTest {

    @Test //TODO: add bad parse statements
    public void parsesCommands() throws Exception{
        StandardScope scope = new StandardScope();
        String code = "" +
                "for(let i=0; i<5; i = i + 1){" +
                "let x = 0;" +
                "}";
        String[] parsedCommands = new String[]{"let i=0"," i<5", " i = i + 1"};

        Loop testLoop = new ForLoop(code, scope);
        assertArrayEquals(parsedCommands, testLoop.getParsedLoopCommands().toArray());
    }

    @Test
    public void parsesBody() throws Exception{
        StandardScope scope = new StandardScope();
        String code = "" +
                "for(let i=0; i<5; i = i + 1){" +
                "let x = 0;" +
                "}";
        String parsedBody = "let x = 0;";

        Loop testLoop = new ForLoop(code, scope);
        assertEquals(testLoop.getLoopBody(), parsedBody);
    }

    @Test
    public void runsLoop() throws Exception{
        StandardScope scope = new StandardScope();
        String code = "" +
                "for(let i=0; i<5; i = i + 1){" +
                "let x = 0;" +
                "}";
        Loop testLoop = new ForLoop(code, scope);
        testLoop.run();
    }
}
