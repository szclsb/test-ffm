package ch.szclsb.main.ffm.export;

import ch.szclsb.main.ffm.*;

import java.lang.foreign.MemorySegment;

public interface Api {
    void printHello() throws Throwable;

    void passHello() throws Throwable;

    String getHello() throws Throwable;

    MemorySegment allocateForeignInt(int value) throws Throwable;

    void freeForeign(MemorySegment pointer) throws Throwable;

    float addFloat(final float a, final float b) throws Throwable;

    void vec4add(final Vector4 a, final Vector4 b, Vector4 r) throws Throwable;

    void pointAddRef(final PointNative a, final PointNative b, PointNative r) throws Throwable;

    PointNative pointAdd(final PointNative a, final PointNative b) throws Throwable;

    Instance createInstance(final int a, final int b) throws Throwable;

    void useInstance(Instance instance) throws Throwable;

    void destroyInstance(Instance instance) throws Throwable;

    int incrementInt(int value) throws Throwable;

    void incrementPInt(NativePointer<NativeInt> pValue) throws Throwable;

    void incrementPpInt(NativePointer<NativePointer<NativeInt>> ppValue) throws Throwable;
}
