package org.ws2811;

import static java.lang.String.*;
import static org.ws2811.preconditions.Preconditions.*;

import org.slf4j.*;
import org.ws2811.exception.*;
import org.ws2811.jni.*;

public class NativeWs2811 implements Ws2811 {
    public static final int DEFAULT_FREQUENCY = 800000;
    public static final int DEFAULT_DMA_NUMBER = 5;
    public static final int DEFAULT_GPIO_NUMBER = 18;
    public static final boolean DEFAULT_INVERT_VALUE = false;
    public static final int DEFAULT_BRIGHTNESS = 255;

    private final Logger mLog = LoggerFactory.getLogger(this.getClass());
    private final Ws2811Channel mChannel;
    private long mReference;

    /**
     * Initialize driver with default values:
     * <ul>
     * <li>GPIO pin number: {@value #DEFAULT_GPIO_NUMBER}</li>
     * <li>Brightness: {@value #DEFAULT_BRIGHTNESS} (0 to 255)</li>
     * <li>Invert: {@value #DEFAULT_INVERT_VALUE} (Set to true if using a
     * inverting level shifter)</li>
     * <li>Frequency: {@value #DEFAULT_FREQUENCY} (Signal rate, 400kHz to
     * 800kHz)</li>
     * <li>DMA number: {@value #DEFAULT_DMA_NUMBER}</li>
     * </ul>
     * 
     * @param pixelCount total number of pixel (/ws2811 chips)
     * @throws DriverInitializationException
     */
    public NativeWs2811(int pixelCount) throws DriverInitializationException {
        this(DEFAULT_FREQUENCY, DEFAULT_DMA_NUMBER, new Ws2811Channel(DEFAULT_GPIO_NUMBER,
                                                                      DEFAULT_INVERT_VALUE,
                                                                      pixelCount,
                                                                      DEFAULT_BRIGHTNESS));
    }

    /**
     * @param frequency Signal rate, 400kHz to 800kHz
     * @param dmaNumber Usually {@value #DEFAULT_DMA_NUMBER}
     * @param channel
     * @throws DriverInitializationException
     */
    public NativeWs2811(int frequency, int dmaNumber, Ws2811Channel channel) throws DriverInitializationException {
        between(frequency, 400000, 800000, "frequency");
        positive(dmaNumber, "dmaNumber");
        notNull(channel, "channel");

        mReference = Ws2811Library.init(frequency, dmaNumber, channel);
        mChannel = channel;

        mLog.debug("Native reference to ws2811 is [{}].", mReference);
        if (mReference == 0)
            throw new DriverInitializationException(format("Could not initialize ws2811 - native driver returned invalid reference (%s)",
                                                           mReference));
    }

    public Ws2811Channel getChannel() {
        return mChannel;
    }

    @Override
    public void render(int[] pixels) throws RenderException {
        int pixelCount = mChannel.getCount();
        if (pixels.length != pixelCount)
            throw new IllegalArgumentException(format("'pixels' length should be '%d' but is '%d'",
                                                      pixelCount,
                                                      pixels.length));

        int result = Ws2811Library.render(mReference, pixels);
        if (result != 0) throw new RenderException();
    }

    @Override
    public void shutdown() {
        try {
            render(new int[mChannel.getCount()]);
        } catch (RenderException exception) {
            mLog.error("Could not turn off pixels before final shutdown", exception);
        }

        Ws2811Library.fini(mReference);
        mReference = 0;
        mLog.info("ws2811 library shut down");
    }

    @Override
    public void waitCompletion() {
        Ws2811Library.waitCompletion(mReference);
    }
}
