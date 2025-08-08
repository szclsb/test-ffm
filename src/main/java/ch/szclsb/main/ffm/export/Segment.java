package ch.szclsb.main.ffm.export;

import java.lang.foreign.MemorySegment;

public interface Segment extends Pointer {
    MemorySegment getSegment();
}
