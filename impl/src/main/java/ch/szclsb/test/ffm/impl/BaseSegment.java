package ch.szclsb.test.ffm.impl;


import ch.szclsb.test.ffm.api.ForeignObject;

import java.lang.foreign.MemorySegment;

public abstract class BaseSegment implements ForeignObject {
    protected final MemorySegment segment;

    public BaseSegment(MemorySegment memorySegment) {
        this.segment = memorySegment;
    }

    @Override
    public MemorySegment getSegment() {
        return segment.asReadOnly();
    }
}
