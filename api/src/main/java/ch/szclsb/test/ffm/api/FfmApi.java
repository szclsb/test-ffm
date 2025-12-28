package ch.szclsb.test.ffm.api;

import ch.szclsb.test.ffm.api.structs.ForeignPoint;
import ch.szclsb.test.ffm.api.values.ForeignInt;

import java.lang.foreign.MemorySegment;

public interface FfmApi {
    void printHello() throws Throwable;

    void passHello() throws Throwable;

    String getHello() throws Throwable;

    MemorySegment allocateForeignInt(int value) throws Throwable;

    void freeForeign(MemorySegment pointer) throws Throwable;

    float addFloat(final float a, final float b) throws Throwable;

//    void vec4add(final Vector4 a, final Vector4 b, Vector4 r) throws Throwable;

    void pointAddRef(final Address<ForeignPoint> a, final Address<ForeignPoint> b, Address<ForeignPoint> r) throws Throwable;

    ForeignPoint pointAdd(final ForeignPoint a, final ForeignPoint b) throws Throwable;

    Address<?> createInstance(final int a, final int b) throws Throwable;

    void useInstance(Address<?> instance) throws Throwable;

    void destroyInstance(Address<?> instance) throws Throwable;

    int incrementInt(int value) throws Throwable;

    void incrementPInt(ForeignInt pValue) throws Throwable;

    void incrementPpInt(AddressPointer<ForeignInt> ppValue) throws Throwable;
}
