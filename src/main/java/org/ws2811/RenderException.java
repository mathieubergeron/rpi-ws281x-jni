package org.ws2811;

public final class RenderException extends Exception {
    private static final long serialVersionUID = 1L;

    public RenderException() {
        super("Rendering failed");
    }
}
