package ch.szclsb.main.ffm;

import ch.szclsb.main.ffm.export.NativePointer;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;

/**
 * Use for pointer to pointers
 */
public class SegmentPointer<T extends NativePointer<?>> implements NativePointer<T> {
    private final MemorySegment segment;
    private final T reference;

    public SegmentPointer(SegmentAllocator allocator, T reference) {
        this.reference = reference;
        this.segment = allocator.allocate(ValueLayout.JAVA_LONG);
        this.segment.set(ValueLayout.JAVA_LONG, 0, reference.getSegment().address());
    }

    @Override
    public MemorySegment getSegment() {
        return segment;
    }

    public T getReference() {
        return reference;
    }
}
