package ch.szclsb.main.ffm.impl;


import ch.szclsb.main.ffm.export.Segment;

import java.lang.foreign.MemorySegment;

public abstract class BaseSegment implements Segment {
    protected final MemorySegment segment;

    public BaseSegment(MemorySegment memorySegment) {
        this.segment = memorySegment;
    }

    @Override
    public MemorySegment getSegment() {
        return segment.asReadOnly();
    }

    @Override
    public MemorySegment getAddress() {
        return segment.reinterpret(0);
    }
}
