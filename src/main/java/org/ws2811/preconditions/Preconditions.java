package org.ws2811.preconditions;

import static java.lang.String.*;

public final class Preconditions {

    // prevent instantiation
    private Preconditions() {}

    public static final <T> T notNull(T value, String name) {
        if (value == null) throw new IllegalArgumentException(format("Parameter '%s' cannot be null", name));

        return value;
    }

    public static final int positive(int value, String name) {
        if (value <= 0)
            throw new IllegalArgumentException(format("Value of '%s' must be > 0, value is '%d'", name, value));

        return value;
    }

    public static final int checkIndex(int value, int size, String name) {
        if (value < 0 || value >= size)
            throw new IllegalArgumentException(format("Index array out of bound for '%s', value must be >= 0 and < %d, value is '%d'",
                                                      name,
                                                      size,
                                                      value));

        return value;
    }

    public static final int between(int value, int min, int max, String name) {
        if (value < min || value > max)
            throw new IllegalArgumentException(format("Value of '%s' must be >= %s and <= %d, value is '%d'",
                                                      name,
                                                      min,
                                                      max,
                                                      value));

        return value;
    }

    public static final int validBrightness(int value, String name) {
        if (value < 0 || value > 255)
            throw new IllegalArgumentException(format("Value of '%s' must be >= 0 and <= 255, value is '%d'",
                                                      name,
                                                      value));

        return value;
    }

    public static final int checkColorValue(int value, String name) {
        if (value < 0 || value > 255)
            throw new IllegalArgumentException(format("Led color value must be >= 0 and <= 255 but value for %s is: %d",
                                                      name,
                                                      value));

        return value;
    }
}
