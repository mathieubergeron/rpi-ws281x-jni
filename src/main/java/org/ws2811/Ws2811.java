package org.ws2811;

import org.ws2811.exception.*;

public interface Ws2811 {

    void render(int[] leds) throws RenderException;

    void shutdown();

    void waitCompletion();

}
