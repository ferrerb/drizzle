package gro.gibberish.drizzle.data;

public final class NumberFormatting {
    private NumberFormatting() {}

    public static String doubleToStringNoDecimals(double x) {
        return String.format("%,.0f", x);
    }

    public static String doubleToStringOneDecimal(double x) {
        return String.format("%,.1f", x);
    }
}
