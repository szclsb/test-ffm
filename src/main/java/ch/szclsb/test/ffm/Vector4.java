package ch.szclsb.test.ffm;

import java.lang.foreign.*;

public class Vector4 {
    public static final ValueLayout.OfFloat VALUE_LAYOUT = ValueLayout.JAVA_FLOAT;
    public static final SequenceLayout SEQUENCE_LAYOUT = MemoryLayout.sequenceLayout(4, VALUE_LAYOUT);

    private final MemorySegment segment;

    public Vector4(MemorySession session) {
        this.segment = MemorySegment.allocateNative(SEQUENCE_LAYOUT, session);
    }

    public void setValues(float x, float y, float z, float w) {
        segment.set(VALUE_LAYOUT, 0, x);
        segment.set(VALUE_LAYOUT, 4, y);
        segment.set(VALUE_LAYOUT, 8, z);
        segment.set(VALUE_LAYOUT, 12, w);
    }

    public MemoryAddress getAddress() {
        return segment.address();
    }

    public float[] toArray() {
        return segment.toArray(VALUE_LAYOUT);
    }
}
