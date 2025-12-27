package ch.szclsb.main.ffm.export;

import ch.szclsb.main.ffm.export.structs.ForeignPoint;
import ch.szclsb.main.ffm.export.values.ForeignInt;
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

    void pointAddRef(final Vector4 a, final Vector4 b, Vector4 r) throws Throwable;

    ForeignPoint pointAdd(final ForeignPoint a, final ForeignPoint b) throws Throwable;

    HasSegment createInstance(final int a, final int b) throws Throwable;

    void useInstance(HasSegment instance) throws Throwable;

    void destroyInstance(HasSegment instance) throws Throwable;

    int incrementInt(int value) throws Throwable;

    void incrementPInt(ForeignInt pValue) throws Throwable;

    void incrementPpInt(AddressPointer<ForeignInt> ppValue) throws Throwable;
}
