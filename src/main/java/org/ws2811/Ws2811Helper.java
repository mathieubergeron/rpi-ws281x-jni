package org.ws2811;

import static org.ws2811.preconditions.Preconditions.*;

public final class Ws2811Helper {

    private Ws2811Helper() {}

    public static int pixelColor(int red, int green, int blue) {
        red = checkColorValue(red, "red") << 16;
        green = checkColorValue(green, "green") << 8;

        return red | green | checkColorValue(blue, "blue");
    }
}
