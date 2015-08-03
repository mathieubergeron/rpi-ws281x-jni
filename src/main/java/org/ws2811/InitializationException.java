package org.ws2811;

public final class InitializationException extends Exception {
    private static final long serialVersionUID = 1L;

    public InitializationException() {
        super("Could not initialize WS2811 native driver");
    }
}
