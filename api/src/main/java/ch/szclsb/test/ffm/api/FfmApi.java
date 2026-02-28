package ch.szclsb.test.ffm.api;

import ch.szclsb.test.ffm.api.pointer.ForeignPointer;
import ch.szclsb.test.ffm.api.structs.ForeignPoint;
import ch.szclsb.test.ffm.api.values.ForeignInt;
import ch.szclsb.test.ffm.api.vectors.ForeignVec4;

import java.lang.foreign.MemorySegment;

public interface FfmApi {
    void printHello() throws Throwable;

    void passHello() throws Throwable;

    String getHello() throws Throwable;

    MemorySegment allocateForeignInt(int value) throws Throwable;

    void freeForeign(MemorySegment pointer) throws Throwable;

    float addFloat(final float a, final float b) throws Throwable;

    void vec4add(final ForeignVec4 a, final ForeignVec4 b, ForeignVec4 r) throws Throwable;

    void pointAddRef(final ForeignPoint a, final ForeignPoint b, ForeignPoint r) throws Throwable;

    ForeignPoint pointAdd(final ForeignPoint a, final ForeignPoint b) throws Throwable;

    ForeignObject createInstance(final int a, final int b) throws Throwable;

    void useInstance(ForeignObject instance) throws Throwable;

    void destroyInstance(ForeignObject instance) throws Throwable;

    int incrementInt(int value) throws Throwable;

    void incrementPInt(ForeignInt pValue) throws Throwable;

    void incrementPpInt(ForeignPointer<ForeignInt> ppValue) throws Throwable;

    void incrementPppInt(ForeignPointer<ForeignPointer<ForeignInt>> pppValue) throws Throwable;
}
