package com.Data;

import com.Data.Data;
import com.Data.DataTypes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataTest {

    @Test
    public void integerInputParse() throws Exception{
        String input = "10";
        Data data = new Data(input);
        assertEquals(data.getData(), 10.0);
    }

    @Test
    public void integerTypeParse() throws Exception{
        String input = "10";
        Data data = new Data(input);
        assertEquals(data.getType(), DataTypes.Number);
    }

    @Test
    public void doubleInputParse() throws Exception{
        String input = "10.95";
        Data data = new Data(input);
        assertEquals(data.getData(), 10.95);
    }

    @Test
    public void doubleTypeParse() throws Exception{
        String input = "10.95";
        Data data = new Data(input);
        assertEquals(data.getType(), DataTypes.Number);
    }

    @Test
    public void stringInputParse() throws Exception{
        String input = "\"Hello\"";
        Data data = new Data(input);
        assertEquals(data.getData(), "Hello");
    }

    @Test
    public void stringTypeParse() throws Exception{
        String input = "\"Hello\"";
        Data data = new Data(input);
        assertEquals(data.getType(), DataTypes.String);
    }

    @Test
    public void booleanTrueInputParse() throws Exception{
        String input = "True";
        Data data = new Data(input);
        assertEquals(data.getData(), true);
    }

    @Test
    public void booleanTrueTypeParse() throws Exception{
        String input = "True";
        Data data = new Data(input);
        assertEquals(data.getType(), DataTypes.Boolean);
    }

    @Test
    public void booleanFalseInputParse() throws Exception{
        String input = "False";
        Data data = new Data(input);
        assertEquals(data.getData(), false);
    }

    @Test
    public void booleanFalseTypeParse() throws Exception{
        String input = "False";
        Data data = new Data(input);
        assertEquals(data.getType(), DataTypes.Boolean);
    }
}
