package ch.szclsb.test.ffm.impl;

import java.lang.foreign.*;

public class Vector4 {
    public static final ValueLayout.OfFloat VALUE_LAYOUT = ValueLayout.JAVA_FLOAT;
    public static final SequenceLayout SEQUENCE_LAYOUT = MemoryLayout.sequenceLayout(4, VALUE_LAYOUT);

    private final MemorySegment segment;

    public Vector4(SegmentAllocator allocator) {
        this.segment = allocator.allocate(SEQUENCE_LAYOUT);
    }

    public void setValues(float x, float y, float z, float w) {
        segment.set(VALUE_LAYOUT, 0, x);
        segment.set(VALUE_LAYOUT, 4, y);
        segment.set(VALUE_LAYOUT, 8, z);
        segment.set(VALUE_LAYOUT, 12, w);
    }

    protected MemorySegment getSegment() {
        return segment.asReadOnly();
    }

    public float[] toArray() {
        return segment.toArray(VALUE_LAYOUT);
    }
}
