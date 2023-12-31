package ch.szclsb.main.ffm.export;

import ch.szclsb.main.ffm.Vector4;

public interface INativeMethodHandler {
    void printHello() throws Throwable;

    void passHello() throws Throwable;

    void vec4add(final Vector4 a, final Vector4 b, Vector4 r) throws Throwable;
}
