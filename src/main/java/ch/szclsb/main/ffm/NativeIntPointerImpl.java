package ch.szclsb.main.ffm;

import ch.szclsb.main.ffm.export.values.IntNative;
import ch.szclsb.main.ffm.export.NativePointer;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;

import static java.lang.foreign.ValueLayout.JAVA_INT;

/**
 * Handles int pointer
 */
public class NativeIntPointerImpl implements NativePointer<IntNative> {
    private final MemorySegment segment;
    private final IntNative api = new IntNative() {
        @Override
        public int getValue() {
            return segment.get(JAVA_INT, 0);
        }

        @Override
        public void setValue(int value) {
            segment.set(JAVA_INT, 0, value);
        }
    };

    public NativeIntPointerImpl(SegmentAllocator allocator) {
        this(allocator, 0);
    }

    public NativeIntPointerImpl(SegmentAllocator allocator, int value) {
        this.segment = allocator.allocate(JAVA_INT);
        this.api.setValue(value);
    }

    public NativeIntPointerImpl(MemorySegment segment) {
        this.segment = segment;
    }

    @Override
    public IntNative getReference() {
        return api;
    }

    @Override
    public MemorySegment getSegment() {
        return segment;
    }
}
