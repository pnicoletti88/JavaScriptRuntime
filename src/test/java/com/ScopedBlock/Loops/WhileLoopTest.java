package com.ScopedBlock.Loops;

import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.Process;
import com.Scopes.StandardScope;
import org.junit.Test;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;


public class WhileLoopTest {

    @Test
    public void errorOnBadCommands1() throws Exception{
        StandardScope scope = new StandardScope();
        String code = "while hi(i<j){}";

        try {
            new WhileLoop(code, scope, mock(Process.class));
            fail();
        } catch(Exception e){
            assertTrue(e instanceof ExternalException);
            assertEquals(((ExternalException)e).getCode(), ExternalErrorCodes.LOOP_ERROR);
        }
    }

    @Test
    public void errorOnBadCommands2() throws Exception{
        StandardScope scope = new StandardScope();
        String code = "while (i<j; j<i){}";

        try {
            new WhileLoop(code, scope, mock(Process.class));
            fail();
        } catch(Exception e){
            assertTrue(e instanceof ExternalException);
            assertEquals(((ExternalException)e).getCode(), ExternalErrorCodes.LOOP_ERROR);
        }
    }

    @Test
    public void errorOnBadCommands3() throws Exception{
        StandardScope scope = new StandardScope();
        String code = "while (){}";

        try {
            new WhileLoop(code, scope, mock(Process.class));
            fail();
        } catch(ExternalException e){
            assertEquals(ExternalErrorCodes.LOOP_ERROR, e.getCode());
        }
    }

}
