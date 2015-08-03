package org.ws2811;


final class Ws2811Library {
    static {
        NativeLibraryLoader.load();
    }

    // Prevent instantiation
    private Ws2811Library() {}

    public static long init(int frequency, int dmanum, Ws2811Channel channel) {
        Ws2811Channel[] channels = new Ws2811Channel[]{channel, null};
        return init(frequency, dmanum, channels);
    }

    public static int render(long reference, int[] leds) {
        return render(reference, 0, leds);
    }

    public static native long init(int frequency, int dmanum, Ws2811Channel[] channels);

    public static native void fini(long reference);

    public static native int render(long reference, int channel, int[] leds);

    public static native int waitCompletion(long reference);
}
