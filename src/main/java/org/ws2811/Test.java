package org.ws2811;

public class Test {
    private static final int FREQUENCY = 800000;
    private static final int DMA_NUMBER = 5;
    private static final int PIXEL_COUNT = 4;
    private static final int GPIO_NUMBER = 18;

    public static void main(String[] args) throws InterruptedException {
        int[] leds = new int[PIXEL_COUNT];
        long reference = Ws2811Library.init(FREQUENCY, DMA_NUMBER, new Ws2811Channel(GPIO_NUMBER, 0, PIXEL_COUNT, 255));
        
        if (reference <= 0) {
            System.out.println("Could not initialize ws2811 device...");
            return;
        }
        
        try {
            for (int i = 0; i < 10; i++) {
                setAll(leds, 0xFFFFFF);
                Ws2811Library.render(reference, leds);
                Ws2811Library.waitCompletion(reference);
                Thread.sleep(1000);

                setAll(leds, 0x000000);
                Ws2811Library.render(reference, leds);
                Ws2811Library.waitCompletion(reference);
                Thread.sleep(1000);
            }
        } finally {
            Ws2811Library.fini(reference);
        }
    }

    private static void setAll(int[] leds, int value) {
        for (int index = 0; index < leds.length; index++) {
            leds[index] = value;
        }
    }
}
