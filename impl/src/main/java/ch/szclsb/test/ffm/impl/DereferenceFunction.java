package ch.szclsb.test.ffm.impl;

import ch.szclsb.test.ffm.api.ForeignObject;

import java.lang.foreign.MemorySegment;
import java.lang.reflect.Type;

@FunctionalInterface
public interface DereferenceFunction<T extends ForeignObject> {
    T apply(MemorySegment segment, int pointerCount, Type refClass);
}
