package gro.gibberish.drizzle.data;

import org.junit.Test;

import gro.gibberish.drizzle.util.NumberFormatting;

import static org.junit.Assert.*;

public class NumberFormattingTest {
    @Test
    public void testDoubleToStringNoDecimals() {
        Double input = 5.9009;
        String expectedResult = "6";
        String result = NumberFormatting.doubleToStringNoDecimals(input);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testDoubleToStringOneDecimal() {
        Double input = 432.44332;
        String expectedResult = "432.4";
        String result = NumberFormatting.doubleToStringOneDecimal(input);
        assertEquals(expectedResult, result);
    }
}