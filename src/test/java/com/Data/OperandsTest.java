package com.Data;

import com.Operands.Operand;
import com.Operands.Operands;
import org.junit.Test;

import static org.junit.Assert.*;

public class OperandsTest {

    @Test
    public void numbersAddition() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(5.5);
        Operand operand = Operands.operandFactory("+");
        Data expected = new Data(9.5);
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersSubtraction() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(5.5);
        Operand operand = Operands.operandFactory("-");
        Data expected = new Data(-1.5);
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersMultiplication() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(5.5);
        Operand operand = Operands.operandFactory("*");
        Data expected = new Data(22.0);
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersDivision() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(8.0);
        Operand operand = Operands.operandFactory("/");
        Data expected = new Data(0.5);
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersEqual() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(4.0);
        Operand operand = Operands.operandFactory("==");
        Data expected = new Data("True");
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersDontEqual() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(5.0);
        Operand operand = Operands.operandFactory("!=");
        Data expected = new Data("True");
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersLessThanEqual1() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(5.0);
        Operand operand = Operands.operandFactory("<=");
        Data expected = new Data("True");
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }
    @Test
    public void numbersLessThanEqual2() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(4.0);
        Operand operand = Operands.operandFactory("<=");
        Data expected = new Data("True");
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersGreaterThanEqual1() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(3.0);
        Operand operand = Operands.operandFactory(">=");
        Data expected = new Data("True");
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }
    @Test
    public void numbersGreaterThanEqual2() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(4.0);
        Operand operand = Operands.operandFactory(">=");
        Data expected = new Data("True");
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersLessThan() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(5.0);
        Operand operand = Operands.operandFactory("<");
        Data expected = new Data("True");
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersGreaterThan() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(3.0);
        Operand operand = Operands.operandFactory(">");
        Data expected = new Data("True");
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }
}

