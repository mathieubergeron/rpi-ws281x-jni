package org.ws2811;

import static java.lang.String.*;
import static org.ws2811.preconditions.Preconditions.*;

import org.slf4j.*;
import org.ws2811.exception.*;

public class SingleChannelWs2811 {
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
    public SingleChannelWs2811(int pixelCount) throws DriverInitializationException {
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
    public SingleChannelWs2811(int frequency, int dmaNumber, Ws2811Channel channel)
            throws DriverInitializationException {
        between(frequency, 400000, 800000, "frequency");
        positive(dmaNumber, "dmaNumber");
        notNull(channel, "channel");

        mReference = Ws2811Library.init(frequency, dmaNumber, channel);
        mChannel = channel;

        if (mReference <= 0)
            throw new DriverInitializationException("Could not initialize WS2811 (native driver returned null)");
    }

    public Ws2811Channel getChannel() {
        return mChannel;
    }

    // TODO: ws2811 is not intended to be used by multiple threads, but shutdown method will probably be called
    // by a VM shutdown hook. So, access to mReference should be thread safe. Will this cause performance issue?
    public void shutdown() {
        try {
            render(new int[mChannel.getCount()]);
        } catch (RenderException exception) {
            mLog.error("Could not turn off pixels before final shutdown", exception);
        }

        Ws2811Library.fini(mReference);
        mReference = 0;
        mLog.info("Ws2811 library shut down");
    }

    public void render(int[] pixels) throws RenderException {
        int pixelCount = mChannel.getCount();
        if (pixels.length != pixelCount)
            throw new IllegalArgumentException(format("'pixels' length should be '%d' but is '%d'",
                                                      pixelCount,
                                                      pixels.length));

        int result = Ws2811Library.render(mReference, pixels);
        if (result != 0) throw new RenderException();
    }

    public void waitCompletion() {
        Ws2811Library.waitCompletion(mReference);
    }
}
