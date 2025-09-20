package ch.szclsb.main.ffm.impl;


import ch.szclsb.main.ffm.export.Native;

import java.lang.foreign.MemorySegment;

public abstract class BaseSegment implements Native {
    protected final MemorySegment segment;

    public BaseSegment(MemorySegment memorySegment) {
        this.segment = memorySegment;
    }

    @Override
    public MemorySegment getSegment() {
        return segment.asReadOnly();
    }
}
