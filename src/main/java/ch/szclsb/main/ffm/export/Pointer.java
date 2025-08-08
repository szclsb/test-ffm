package ch.szclsb.main.ffm.export;

import java.lang.foreign.MemorySegment;

public interface Pointer {
    /**
     * Returns a zero length memory segment pointing to an address
     *
     * @return memory segment
     */
    MemorySegment getAddress();

    Pointer NULL = () -> MemorySegment.NULL;
}
