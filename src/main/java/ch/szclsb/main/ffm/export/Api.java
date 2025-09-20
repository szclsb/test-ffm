package ch.szclsb.main.ffm.export;

import ch.szclsb.main.ffm.export.structs.Point;
import ch.szclsb.main.ffm.export.values.IntNative;
import ch.szclsb.main.ffm.impl.Vector4;

import java.lang.foreign.MemorySegment;

public interface Api {
    void printHello() throws Throwable;

    void passHello() throws Throwable;

    String getHello() throws Throwable;

    MemorySegment allocateForeignInt(int value) throws Throwable;

    void freeForeign(MemorySegment pointer) throws Throwable;

    float addFloat(final float a, final float b) throws Throwable;

    void vec4add(final Vector4 a, final Vector4 b, Vector4 r) throws Throwable;

    void pointAddRef(final Address<Point> a, final Address<Point> b, Address<Point> r) throws Throwable;

    Point pointAdd(final Point a, final Point b) throws Throwable;

    Pointer createInstance(final int a, final int b) throws Throwable;

    void useInstance(Pointer instance) throws Throwable;

    void destroyInstance(Pointer instance) throws Throwable;

    int incrementInt(int value) throws Throwable;

    void incrementPInt(Address<IntNative> pValue) throws Throwable;

    void incrementPpInt(Address<Address<IntNative>> ppValue) throws Throwable;
}
