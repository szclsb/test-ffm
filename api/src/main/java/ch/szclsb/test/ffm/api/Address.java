package ch.szclsb.test.ffm.api;

import java.lang.foreign.MemorySegment;

public interface Address<T extends HasAddress<?>> extends HasSegment {
    Address<?> NULL = () -> MemorySegment.NULL;
}
