package ch.szclsb.main.ffm.export;

import java.lang.foreign.MemorySegment;

public interface Address<T extends HasAddress<?>> extends HasSegment {
    Address<?> NULL = () -> MemorySegment.NULL;
}
