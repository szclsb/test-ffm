package ch.szclsb.main.ffm.export;

import ch.szclsb.main.ffm.PointNative;
import ch.szclsb.main.ffm.Vector4;

public interface INativeMethodHandler {
    void printHello() throws Throwable;

    void passHello() throws Throwable;

    void vec4add(final Vector4 a, final Vector4 b, Vector4 r) throws Throwable;

    void pointAddRef(final PointNative a, final PointNative b, PointNative r) throws Throwable;

    PointNative pointAdd(final PointNative a, final PointNative b) throws Throwable;
}
