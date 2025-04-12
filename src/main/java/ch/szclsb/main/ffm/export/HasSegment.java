package ch.szclsb.main.ffm.export;

import java.lang.foreign.MemorySegment;

public interface HasSegment extends Native {
    MemorySegment getSegment();
}
