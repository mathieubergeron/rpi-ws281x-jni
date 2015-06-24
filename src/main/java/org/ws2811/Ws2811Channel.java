package org.ws2811;

public final class Ws2811Channel {
    private final int mGpio;
    private final int mInvert;
    private final int mCount;
    private final int mBrightness;
    
    public Ws2811Channel(int gpio, int invert, int count, int brightness) {
        mGpio = gpio;
        mInvert = invert;
        mCount = count;
        mBrightness = brightness;
    }
    
    public int getGpio() {
        return mGpio;
    }
    
    public int getInvert() {
        return mInvert;
    }
    
    public int getCount() {
        return mCount;
    }
    
    public int getBrightness() {
        return mBrightness;
    }
}
