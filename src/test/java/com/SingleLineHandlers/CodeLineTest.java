package com.SingleLineHandlers;

import com.Data.Data;
import com.Scopes.StandardScope;
import com.SingleLineHandlers.CodeLine;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;


public class CodeLineTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void numberAssignment() throws Exception{
        String code = "let i=10";
        StandardScope scope = mock(StandardScope.class);
        ArgumentCaptor<Data> argData = ArgumentCaptor.forClass(Data.class);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);

        CodeLine testLine = new CodeLine(code, scope);
        testLine.runAndReturnResult();

        verify(scope).createVariable(argString.capture(), argData.capture());
        assertEquals("i", argString.getValue());
        assertEquals(10.0, argData.getValue().getData());
    }

    @Test
    public void stringAssignment() throws Exception{
        String code = "let i=\"apples\"";
        StandardScope scope = mock(StandardScope.class);
        ArgumentCaptor<Data> argData = ArgumentCaptor.forClass(Data.class);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);

        CodeLine testLine = new CodeLine(code, scope);
        testLine.runAndReturnResult();

        verify(scope).createVariable(argString.capture(), argData.capture());
        assertEquals("i", argString.getValue());
        assertEquals("apples", argData.getValue().getData());
    }

    @Test
    public void numberAddition() throws Exception{
        String code = "let i=10+15.2";
        StandardScope scope = mock(StandardScope.class);
        ArgumentCaptor<Data> argData = ArgumentCaptor.forClass(Data.class);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);

        CodeLine testLine = new CodeLine(code, scope);
        testLine.runAndReturnResult();

        verify(scope).createVariable(argString.capture(), argData.capture());
        assertEquals("i", argString.getValue());
        assertEquals(25.2, argData.getValue().getData());
    }

    @Test
    public void stringAddition() throws Exception{
        String code = "let i=\"apple\"+\"pear\"";
        StandardScope scope = mock(StandardScope.class);
        ArgumentCaptor<Data> argData = ArgumentCaptor.forClass(Data.class);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);

        CodeLine testLine = new CodeLine(code, scope);
        testLine.runAndReturnResult();

        verify(scope).createVariable(argString.capture(), argData.capture());
        assertEquals("i", argString.getValue());
        assertEquals("applepear", argData.getValue().getData());
    }

    @Test
    public void stringAndNumberAddition() throws Exception{
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("Math can only be performed on the same type");

        String code = "let i=\"apple\"+10";
        StandardScope scope = mock(StandardScope.class);

        CodeLine testLine = new CodeLine(code, scope);
        testLine.runAndReturnResult();
    }

    @Test
    public void brokenSyntax() throws Exception{
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("Poorly formatted expression");

        String code = "let i=\"apple\"+";
        StandardScope scope = mock(StandardScope.class);

        CodeLine testLine = new CodeLine(code, scope);
        testLine.runAndReturnResult();
    }

    @Test
    public void brokenSyntax2() throws Exception{
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("Poorly formatted expression");

        String code = "let i=+\"apple\"";
        StandardScope scope = mock(StandardScope.class);

        CodeLine testLine = new CodeLine(code, scope);
        testLine.runAndReturnResult();
    }

    @Test
    public void numbersEqual() throws Exception{
        String code = "let i=11==11";
        StandardScope scope = mock(StandardScope.class);
        ArgumentCaptor<Data> argData = ArgumentCaptor.forClass(Data.class);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);

        CodeLine testLine = new CodeLine(code, scope);
        testLine.runAndReturnResult();

        verify(scope).createVariable(argString.capture(), argData.capture());
        assertEquals("i", argString.getValue());
        assertEquals(true, argData.getValue().getData());
    }

    @Test
    public void booleanEqual() throws Exception{
        String code = "let i=True==True";
        StandardScope scope = mock(StandardScope.class);
        ArgumentCaptor<Data> argData = ArgumentCaptor.forClass(Data.class);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);

        CodeLine testLine = new CodeLine(code, scope);
        testLine.runAndReturnResult();

        verify(scope).createVariable(argString.capture(), argData.capture());
        assertEquals("i", argString.getValue());
        assertEquals(true, argData.getValue().getData());
    }

    @Test
    public void booleanAnd() throws Exception{
        String code = "let i=True&&True";
        StandardScope scope = mock(StandardScope.class);
        ArgumentCaptor<Data> argData = ArgumentCaptor.forClass(Data.class);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);

        CodeLine testLine = new CodeLine(code, scope);
        testLine.runAndReturnResult();

        verify(scope).createVariable(argString.capture(), argData.capture());
        assertEquals("i", argString.getValue());
        assertEquals(true, argData.getValue().getData());
    }

    @Test
    public void booleanAndFalse() throws Exception{
        String code = "let i=True&&False";
        StandardScope scope = mock(StandardScope.class);
        ArgumentCaptor<Data> argData = ArgumentCaptor.forClass(Data.class);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);

        CodeLine testLine = new CodeLine(code, scope);
        testLine.runAndReturnResult();

        verify(scope).createVariable(argString.capture(), argData.capture());
        assertEquals("i", argString.getValue());
        assertEquals(false, argData.getValue().getData());
    }

    @Test
    public void booleanOr() throws Exception{
        String code = "let i=True||False";
        StandardScope scope = mock(StandardScope.class);
        ArgumentCaptor<Data> argData = ArgumentCaptor.forClass(Data.class);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);

        CodeLine testLine = new CodeLine(code, scope);
        testLine.runAndReturnResult();

        verify(scope).createVariable(argString.capture(), argData.capture());
        assertEquals("i", argString.getValue());
        assertEquals(true, argData.getValue().getData());
    }
    @Test
    public void booleanComplex() throws Exception{
        String code = "let i=10 < 11 && True";
        StandardScope scope = mock(StandardScope.class);
        ArgumentCaptor<Data> argData = ArgumentCaptor.forClass(Data.class);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);

        CodeLine testLine = new CodeLine(code, scope);
        testLine.runAndReturnResult();

        verify(scope).createVariable(argString.capture(), argData.capture());
        assertEquals("i", argString.getValue());
        assertEquals(true, argData.getValue().getData());
    }
}
