package org.ws2811;

import org.ws2811.exception.*;

public class Test {
    private static final int DEFAULT_LED_COUNT = 4;

    public static void main(String[] args) throws Exception {
        final SingleChannelWs2811 ws2811 = new SingleChannelWs2811(DEFAULT_LED_COUNT);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                setAll(ws2811, 0);
                try {
                    ws2811.render();
                } catch (RenderException exception) {
                    // Too bad
                }
                ws2811.shutdown();
            }
        });

        while (true) {
            setAll(ws2811, 255);
            ws2811.render();
            Thread.sleep(150);

            setAll(ws2811, 0);
            ws2811.render();
            Thread.sleep(150);
        }
    }

    private static void setAll(SingleChannelWs2811 ws2811, int value) {
        for (int index = 0; index < ws2811.getChannel().getCount(); index++) {
            ws2811.setValue(index, value, value, value);
        }
    }
}
