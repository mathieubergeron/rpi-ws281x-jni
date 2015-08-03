package org.ws2811;

import static java.lang.String.*;

public class SingleChannelWs2811 {
    public static final int DEFAULT_FREQUENCY = 800000;
    public static final int DEFAULT_DMA_NUMBER = 5;
    public static final int DEFAULT_GPIO_NUMBER = 18;
    public static final int DEFAULT_INVERT_VALUE = 0;
    public static final int DEFAULT_BRIGHTNESS = 255;

    private final long mReference;
    private final int[] mPixels;

    public SingleChannelWs2811(int pixelCount) throws InitializationException {
        this(DEFAULT_FREQUENCY, DEFAULT_DMA_NUMBER, new Ws2811Channel(DEFAULT_GPIO_NUMBER,
                                                                      DEFAULT_INVERT_VALUE,
                                                                      pixelCount,
                                                                      DEFAULT_BRIGHTNESS));
    }

    public SingleChannelWs2811(int frequency, int dmaNumber, Ws2811Channel channel) throws InitializationException {
        mPixels = new int[channel.getCount()];
        mReference = Ws2811Library.init(frequency, dmaNumber, channel);

        if (mReference <= 0) throw new InitializationException();
    }

    public void shutdown() {
        Ws2811Library.fini(mReference);
    }

    public void render() throws RenderException {
        render(false);
    }

    public void render(boolean wait) throws RenderException {
        int result = Ws2811Library.render(mReference, mPixels);
        if (result != 0) throw new RenderException();

        if (wait) {
            waitCompletion();
        }
    }

    public void waitCompletion() {
        Ws2811Library.waitCompletion(mReference);
    }

    public void setValue(int pixelNumber, int red, int green, int blue) {
        red = checkLedValue(red) << 16;
        green = checkLedValue(green) << 8;
        checkLedValue(blue);

        mPixels[pixelNumber] = red | green | blue;
    }

    private final int checkLedValue(int value) {
        if (value < 0 || value > 255)
            throw new IllegalArgumentException(format("Led color value must be >= 0 and <= 255 but is: %d", value));

        return value;
    }
}
