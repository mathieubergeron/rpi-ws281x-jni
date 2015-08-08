package org.ws2811;

import static org.ws2811.preconditions.Preconditions.*;

public class Ws2811Channel {
    private final int mGpio;
    private final boolean mInvert;
    private final int mCount;
    private final int mBrightness;

    /**
     * @param gpio Must be a PWM gpio pin
     * @param invert Set to true if using a inverting level shifter
     * @param count Total number of pixel (/ws2811 chips)
     * @param brightness 0 to 255
     */
    public Ws2811Channel(int gpio, boolean invert, int count, int brightness) {
        mGpio = positive(gpio, "gpio");
        mInvert = invert;
        mCount = positive(count, "count");
        mBrightness = validBrightness(brightness, "brightness");
    }

    public int getGpio() {
        return mGpio;
    }

    public int getInvert() {
        return mInvert ? 1 : 0;
    }

    public int getCount() {
        return mCount;
    }

    public int getBrightness() {
        return mBrightness;
    }

    @Override
    public String toString() {
        return "Ws2811Channel [mGpio="
               + mGpio
               + ", mInvert="
               + mInvert
               + ", mCount="
               + mCount
               + ", mBrightness="
               + mBrightness
               + "]";
    }
}
