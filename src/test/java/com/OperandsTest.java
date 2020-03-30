package com;

import com.Data.Data;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class OperandsTest {
    @Test
    public void splitOnMathOperands(){
        String input = "apple+pear-bill*tom / tim";
        String[] expected = new String[]{"apple", "pear", "bill", "tom ", " tim"};
        ArrayList<String> result = Operands.splitOnOperand(input);
        assertArrayEquals(result.toArray(), expected);
    }

    @Test
    public void splitOnBoolean(){
        String input = "apple==pear<=bill>=tom !=tim";
        String[] expected = new String[]{"apple", "pear", "bill", "tom ", "tim"};
        ArrayList<String> result = Operands.splitOnOperand(input);
        assertArrayEquals(result.toArray(), expected);
    }

    @Test
    public void splitRespectsQuotedOperands(){
        String input = "apple==\"pear<=bill\">=tom !=tim";
        String[] expected = new String[]{"apple", "\"pear<=bill\"", "tom ", "tim"};
        ArrayList<String> result = Operands.splitOnOperand(input);
        assertArrayEquals(result.toArray(), expected);
    }

    @Test
    public void parseMathOperands(){
        String input = "apple+pear-bill*tom / tim";
        String[] expected = new String[]{"+","-","*","/"};
        ArrayList<String> result = Operands.parseOperands(input);
        assertArrayEquals(result.toArray(), expected);
    }

    @Test
    public void parseBooleanOperands(){
        String input = "apple==pear<=bill>=tom !=tim";
        String[] expected = new String[]{"==","<=",">=","!="};
        ArrayList<String> result = Operands.parseOperands(input);
        assertArrayEquals(result.toArray(), expected);
    }

    @Test
    public void parseCombinedOperands(){
        String input = "/*==+-";
        String[] expected = new String[]{"/","*","==","+","-"};
        ArrayList<String> result = Operands.parseOperands(input);
        assertArrayEquals(result.toArray(), expected);
    }

    @Test
    public void parseOperandsRespectsQuotes(){
        String input = "apple==\"pear<=bill\">=tom !=tim";
        String[] expected = new String[]{"==",">=","!="};
        ArrayList<String> result = Operands.parseOperands(input);
        assertArrayEquals(result.toArray(), expected);
    }

    @Test
    public void numbersAddition() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(5.5);
        String operand = "+";
        Data expected = new Data(9.5);
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersSubtraction() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(5.5);
        String operand = "-";
        Data expected = new Data(-1.5);
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersMultiplication() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(5.5);
        String operand = "*";
        Data expected = new Data(22.0);
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersDivision() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(8.0);
        String operand = "/";
        Data expected = new Data(0.5);
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersEqual() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(4.0);
        String operand = "==";
        Data expected = new Data("True");
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersDontEqual() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(5.0);
        String operand = "!=";
        Data expected = new Data("True");
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersLessThanEqual1() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(5.0);
        String operand = "<=";
        Data expected = new Data("True");
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }
    @Test
    public void numbersLessThanEqual2() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(4.0);
        String operand = "<=";
        Data expected = new Data("True");
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersGreaterThanEqual1() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(3.0);
        String operand = ">=";
        Data expected = new Data("True");
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }
    @Test
    public void numbersGreaterThanEqual2() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(4.0);
        String operand = ">=";
        Data expected = new Data("True");
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersLessThan() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(5.0);
        String operand = "<";
        Data expected = new Data("True");
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }

    @Test
    public void numbersGreaterThan() throws Exception{
        Data a = new Data(4.0);
        Data b = new Data(3.0);
        String operand = ">";
        Data expected = new Data("True");
        Data result = Operands.evaluate(a,b,operand);
        assertEquals(expected.getData(), result.getData());
    }
}

