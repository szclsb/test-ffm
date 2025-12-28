package ch.szclsb.test.ffm.api;

import java.lang.foreign.MemorySegment;

public interface HasSegment {
    MemorySegment getSegment();
}
