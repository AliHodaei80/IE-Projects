package ir.ie.mizdooni.utils;

public class NumberRange {
    private static final double MIN_VALUE = 0;
    private static final double MAX_VALUE = 5;
    private static final double STEP = 0.5;

    public static boolean isInRange(double value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            return false;
        }
        double normalizedValue = value - MIN_VALUE;
        return normalizedValue % STEP == 0;
    }
}
