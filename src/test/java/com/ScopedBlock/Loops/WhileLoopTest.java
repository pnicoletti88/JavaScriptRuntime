package com.ScopedBlock.Loops;

import com.Scopes.StandardScope;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertArrayEquals;


public class WhileLoopTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();


    @Test
    public void errorOnBadCommands1() throws Exception{
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("Invalid loop syntax");

        StandardScope scope = new StandardScope();
        String code = "while hi(i<j){}";

        new WhileLoop(code, scope);
    }

    @Test
    public void errorOnBadCommands2() throws Exception{
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("Illegal Loop Format");

        StandardScope scope = new StandardScope();
        String code = "while (i<j; j<i){}";

        new WhileLoop(code, scope);
    }

    @Test
    public void errorOnBadCommands3() throws Exception{
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("Illegal Loop Format");

        StandardScope scope = new StandardScope();
        String code = "while (){}";

        new WhileLoop(code, scope);
    }

}
