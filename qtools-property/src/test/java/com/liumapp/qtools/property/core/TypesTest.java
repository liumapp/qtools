package com.liumapp.qtools.property.core;


import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


/**
 * file TypesTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/7
 */
public class TypesTest {

    @Test
    public void testAsString() throws Exception {
        final String actualString = "actually a string";
        final Integer number = 54;
        final List<Integer> list = Arrays.asList(4, 3, 8);
        Assert.assertEquals(actualString, Types.asString(actualString));
        Assert.assertEquals("54", Types.asString(number));
        Assert.assertEquals("[4, 3, 8]", Types.asString(list));
    }

    @Test
    public void testStrictAsString() throws Exception {
        final String actualString = "actually a string";
        final Integer number = 54;
        final List<Integer> list = Arrays.asList(4, 3, 8);
        Assert.assertEquals(actualString, Types.strictAsString(actualString));
        Assert.assertEquals(null, Types.strictAsString(number));
        Assert.assertEquals(null, Types.strictAsString(list));
    }

    @Test
    public void testAsFloat() throws Exception {
        final float actuallyFloat = 1.45f;
        final String floatString = "1.45";
        final int integer = 4;
        Assert.assertEquals((Float) actuallyFloat, Types.asFloat(actuallyFloat));
        Assert.assertEquals((Float) 1.45f, Types.asFloat(floatString));
        Assert.assertEquals((Float) 4f, Types.asFloat(integer));
    }

    @Test
    public void testStrictAsFloat() throws Exception {
        final float actuallyFloat = 1.45f;
        final String floatString = "1.45";
        final int integer = 4;
        Assert.assertEquals((Float) actuallyFloat, Types.strictAsFloat(actuallyFloat));
        Assert.assertEquals(null, Types.strictAsFloat(floatString));
        Assert.assertEquals((Float) 4f, Types.strictAsFloat(integer));

    }

    @Test
    public void testAsDouble() throws Exception {
        final double actuallyDouble = 1.45f;
        final String doubleString = "1.45";
        final int integer = 4;
        Assert.assertEquals((Double) actuallyDouble, Types.asDouble(actuallyDouble));
        Assert.assertEquals((Double) 1.45d,  Types.asDouble(doubleString));
        Assert.assertEquals((Double) 4d, Types.asDouble(integer));

    }

    @Test
    public void testStrictAsDouble() throws Exception {
        final double actuallyDouble = 1.45f;
        final String doubleString = "1.45";
        final int integer = 4;
        Assert.assertEquals((Double) actuallyDouble, Types.strictAsDouble(actuallyDouble));
        Assert.assertEquals(null, Types.strictAsDouble(doubleString));
        Assert.assertEquals((Double) 4d, Types.strictAsDouble(integer));

    }

    @Test
    public void testAsInt() throws Exception {
        final int actuallyInt = 4;
        final String doubleString = "42";
        final float integer = 4f;
        Assert.assertEquals((Integer) actuallyInt, Types.asInt(actuallyInt));
        Assert.assertEquals((Integer) 42,  Types.asInt(doubleString));
        Assert.assertEquals((Integer) 4, Types.asInt(integer));
    }

    @Test
    public void testStrictAsInt() throws Exception {
        final int actuallyInt = 4;
        final String doubleString = "42";
        final float integer = 4f;
        Assert.assertEquals((Integer) actuallyInt, Types.strictAsInt(actuallyInt));
        Assert.assertEquals(null, Types.strictAsInt(doubleString));
        Assert.assertEquals(null, Types.strictAsInt(integer));
    }

    @Test
    public void testAsLong() throws Exception {
        final long actuallyInt = 4934285231847238472L;
        final String doubleString = "424338492842";
        final double integer = 4f;
        Assert.assertEquals((Long) actuallyInt, Types.asLong(actuallyInt));
        Assert.assertEquals((Long) 424338492842L,  Types.asLong(doubleString));
        Assert.assertEquals((Long) 4L, Types.asLong(integer));
    }

    @Test
    public void testStrictAsLong() throws Exception {
        final long actuallyInt = 4934285231847238472L;
        final String doubleString = "424338492842";
        final double integer = 4f;
        Assert.assertEquals((Long) actuallyInt, Types.strictAsLong(actuallyInt));
        Assert.assertEquals(null, Types.strictAsLong(doubleString));
        Assert.assertEquals(null, Types.strictAsLong(integer));
    }

    @Test
    public void testAsBoolean() throws Exception {
        final boolean actual = true;
        final String[] trueEvaluating = new String[] {"true", "yes", "1", "t", "y"}, falseEvaluating = new String[]
                {"false", "no", "0", "f", "n"};
        Assert.assertEquals(actual, Types.asBoolean(actual));
        for (String val : trueEvaluating) {
            Assert.assertEquals(true, Types.asBoolean(val));
        }

        for (String val : falseEvaluating) {
            Assert.assertEquals(false, Types.asBoolean(val));
        }
    }

    @Test
    public void testStrictAsBoolean() throws Exception {
        final boolean actual = true;
        final String[] trueEvaluating = new String[] {"true", "yes", "1", "t", "y"}, falseEvaluating = new String[]
                {"false", "no", "0", "f", "n"};
        Assert.assertEquals(actual, Types.strictAsBoolean(actual));
        for (String val : trueEvaluating) {
            Assert.assertEquals(null, Types.strictAsBoolean(val));
        }

        for (String val : falseEvaluating) {
            Assert.assertEquals(null, Types.strictAsBoolean(val));
        }
    }
}