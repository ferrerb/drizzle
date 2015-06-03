package gro.gibberish.drizzle.data;

/**
 * Change this
 */
public final class NumberFormatting {
    private NumberFormatting() {}

    public static String doubleToStringNoDecimals(double x) {
        return String.format("%1$, .0f", x);
    }

    public static String doubleToStringOneDecimal(double x) {
        return String.format("%1$, .1f", x);
    }
}
