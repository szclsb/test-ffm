package ch.szclsb.main.ffm.impl;


import ch.szclsb.main.ffm.export.HasSegment;

import java.lang.foreign.MemorySegment;

public abstract class BaseSegment implements HasSegment {
    protected final MemorySegment segment;

    public BaseSegment(MemorySegment memorySegment) {
        this.segment = memorySegment;
    }

    @Override
    public MemorySegment getSegment() {
        return segment.asReadOnly();
    }
}
