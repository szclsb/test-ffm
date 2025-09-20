package ch.szclsb.main.ffm.export;

import java.lang.foreign.MemorySegment;

public interface Native {
    MemorySegment getSegment();
}
