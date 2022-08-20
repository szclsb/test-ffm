package ch.szclsb.test.ffm;

public interface INativeMethodHandler {
    void printHello() throws Throwable;

    void vec4add(final Vector4 a, final Vector4 b, Vector4 r) throws Throwable;
}
