package com.ScopedBlock.Loops;

import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.Scopes.StandardScope;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;


public class ForLoopTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void parsesCommands() throws Exception{
        StandardScope scope = new StandardScope();
        String code = "" +
                "for(let i=0; i<5; i = i + 1){" +
                "let x = 0;" +
                "}";
        String[] parsedCommands = new String[]{"let i=0"," i<5", " i = i + 1"};

        ForLoop testLoop = new ForLoop(code, scope);
        assertArrayEquals(parsedCommands, testLoop.getParsedLoopCommands().toArray());
    }

    @Test
    public void errorOnBadCommands1() throws Exception{
        StandardScope scope = new StandardScope();
        String code = "for hi(let i=0; i<5; i = i + 1){}";
        try{
            new ForLoop(code, scope);
        } catch(ExternalException e){
            assertEquals(ExternalErrorCodes.LOOP_ERROR, e.getCode());
        }
    }

    @Test
    public void errorOnBadCommands2() throws Exception{
        StandardScope scope = new StandardScope();
        String code = "for(;let i=0; i<5; i = i + 1){}";

        try{
            new ForLoop(code, scope);
        } catch(ExternalException e){
            assertEquals(ExternalErrorCodes.LOOP_ERROR, e.getCode());
        }
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

   @Test
    public void runsNoThirdParam() throws Exception{
        StandardScope scope = new StandardScope();
        String code = "" +
                "for(let i=0; i<5;){" +
                "let x = 0;" +
                "i = i + 1;" +
                "}";
        Loop testLoop = new ForLoop(code, scope);
        testLoop.run();
    }
}
