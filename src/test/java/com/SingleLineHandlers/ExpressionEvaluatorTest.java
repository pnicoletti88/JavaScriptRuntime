package com.SingleLineHandlers;

import com.Data.Data;
import com.Data.DataTypes;
import com.Scopes.StandardScope;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExpressionEvaluatorTest {

    @Test
    public void correctlyEvaluatesSingleString() throws Exception{
        String input = "\"Hello()\"";
        Data result = new ExpressionEvaluator(input, new StandardScope()).getResult();
        assertEquals(result.getType(), DataTypes.String);
        assertEquals(result.getData(), "Hello()");
    }

    @Test
    public void correctlyEvaluatesSingleNumber() throws Exception{
        String input = "15";
        Data result = new ExpressionEvaluator(input, new StandardScope()).getResult();
        assertEquals(result.getType(), DataTypes.Number);
        assertEquals((double)result.getData(), 15.0, 10e-10);
    }

    @Test
    public void correctlyEvaluatesSingleBoolean() throws Exception{
        String input = "True";
        Data result = new ExpressionEvaluator(input, new StandardScope()).getResult();
        assertEquals(result.getType(), DataTypes.Boolean);
        assertTrue((boolean) result.getData());
    }

    @Test
    public void correctlyPerformsBracketMath1() throws Exception{
        String input = "(1 + (2 - 4))*(3+4)";
        Data result = new ExpressionEvaluator(input, new StandardScope()).getResult();
        assertEquals(result.getType(), DataTypes.Number);
        assertEquals((double)result.getData(), -7.0, 10e-10);
    }

    @Test
    public void correctlyPerformsBracketMath2() throws Exception{
        String input = "((((2 * ((1 + (2 - 4))*(3+4))))/4))";
        Data result = new ExpressionEvaluator(input, new StandardScope()).getResult();
        assertEquals(result.getType(), DataTypes.Number);
        assertEquals((double)result.getData(), -3.5, 10e-10);
    }

}
