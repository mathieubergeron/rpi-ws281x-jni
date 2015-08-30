package org.ws2811;

public class Test {
    private static final int DEFAULT_LED_COUNT = 4;

    public static void main(String[] args) throws Exception {
        final SingleChannelWs2811 ws2811 = new SingleChannelWs2811(DEFAULT_LED_COUNT);
        int[] pixels = new int[DEFAULT_LED_COUNT];

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                ws2811.shutdown();
            }
        });

        while (true) {
            setAll(pixels, 255);
            ws2811.render(pixels);
            Thread.sleep(150);

            setAll(pixels, 0);
            ws2811.render(pixels);
            Thread.sleep(150);
        }
    }

    private static void setAll(int[] pixels, int value) {
        for (int index = 0; index < pixels.length; index++) {
            pixels[index] = Ws2811Helper.pixelColor(value, value, value);
        }
    }
}
