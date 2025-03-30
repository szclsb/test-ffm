package ch.szclsb.main.ffm;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;

import static java.lang.foreign.ValueLayout.ADDRESS;

public class Instance {
    public static final MemoryLayout LAYOUT = ADDRESS;

    private final MemorySegment segment;

    public Instance(SegmentAllocator allocator) {
        this.segment = allocator.allocate(LAYOUT);
    }

    public Instance(MemorySegment segment) {
        this.segment = segment;
    }

    public MemorySegment getSegment() {
        return segment.asReadOnly();
    }
}
