package com.ScopedBlock.Conditionals;

import com.Process;
import com.Scopes.Scope;
import com.Scopes.StandardScope;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class IfStatementTest {
    @Test
    public void runsIfWhenConditionTrue() throws Exception{
        Scope parentScope = new StandardScope();
        String code = "" +
                "if(True){" +
                "let i=10;" +
                "}";
        String serialState = "i-10.0-Number\n";

        IfStatement target = new IfStatement(code, parentScope, mock(Process.class));
        target.run();
        assertEquals(serialState, target.getScope().serialize());
    }

    @Test
    public void doesNotRunIfWhenFalse() throws Exception{
        Scope parentScope = new StandardScope();
        String code = "" +
                "if(False){" +
                "let i=10;" +
                "}";
        String serialState = "";

        IfStatement target = new IfStatement(code, parentScope, mock(Process.class));
        target.run();
        assertEquals(serialState, target.getScope().serialize());
    }

    @Test
    public void runsElseWhenIfFalse() throws Exception{
        Scope parentScope = new StandardScope();
        String code = "" +
                "if(False){" +
                "let i=10;" +
                "} else {" +
                "let i=6;" +
                "}";
        String serialState = "i-6.0-Number\n";

        IfStatement target = new IfStatement(code, parentScope, mock(Process.class));
        target.run();
        assertEquals(serialState, target.getScope().serialize());
    }

    @Test
    public void runsIfNotElseWhenTrue() throws Exception{
        Scope parentScope = new StandardScope();
        String code = "" +
                "if(True){" +
                "let i=10;" +
                "} else {" +
                "let i=6;" +
                "}";
        String serialState = "i-10.0-Number\n";

        IfStatement target = new IfStatement(code, parentScope, mock(Process.class));
        target.run();
        assertEquals(serialState, target.getScope().serialize());
    }

    @Test
    public void runsElseIf() throws Exception{
        Scope parentScope = new StandardScope();
        String code = "" +
                "if(False){" +
                "let i=10;" +
                "} else if (True) {" +
                "let i=6;" +
                "} else {" +
                "let i=8;" +
                "}";
        String serialState = "i-6.0-Number\n";

        IfStatement target = new IfStatement(code, parentScope, mock(Process.class));
        target.run();
        assertEquals(serialState, target.getScope().serialize());
    }
}
