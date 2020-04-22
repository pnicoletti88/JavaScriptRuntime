package com.ScopedBlock.Loops;

import com.Exceptions.ExternalErrorCodes;
import com.Exceptions.ExternalException;
import com.Exceptions.InternalErrorCodes;
import com.Exceptions.InternalException;
import com.Scopes.StandardScope;
import org.junit.Test;


import static org.junit.Assert.*;


public class WhileLoopTest {

    @Test
    public void errorOnBadCommands1() throws Exception{
        StandardScope scope = new StandardScope();
        String code = "while hi(i<j){}";

        try {
            new WhileLoop(code, scope);
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
            new WhileLoop(code, scope);
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
            new WhileLoop(code, scope);
            fail();
        } catch(ExternalException e){
            assertEquals(ExternalErrorCodes.LOOP_ERROR, e.getCode());
        }
    }

}
