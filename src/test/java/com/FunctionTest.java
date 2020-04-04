package com;

import com.Data.Data;
import com.Functions.Function;
import com.Scopes.StandardScope;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FunctionTest {
    @Test
    public void declaresFunction() throws Exception{
        String input = "function hello(a,b,d){let x=1;}";

        StandardScope scope = mock(StandardScope.class);
        ArgumentCaptor<Data> argData = ArgumentCaptor.forClass(Data.class);
        ArgumentCaptor<String> argString = ArgumentCaptor.forClass(String.class);

        Function.declareFunction(input, scope);

        verify(scope).createVariable(argString.capture(), argData.capture());
        assertEquals("hello", argString.getValue());
        assertEquals("function hello(a,b,d){let x=1;}", argData.getValue().getData());
    }
}
