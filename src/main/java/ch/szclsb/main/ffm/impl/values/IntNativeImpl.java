package ch.szclsb.main.ffm.impl.values;

import ch.szclsb.main.ffm.export.Address;
import ch.szclsb.main.ffm.export.values.IntNative;
import ch.szclsb.main.ffm.impl.BaseSegment;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;

/**
 * Handles int pointer
 */
public class IntNativeImpl extends BaseSegment implements Address<Integer> {
    private static final Lay

    private IntNativeImpl(MemorySegment memorySegment) {
        super(memorySegment);
    }

    @Override
    public int getValue() {
        return segment.get(LAYOUT, 0);
    }

    @Override
    public void setValue(Integer value) {
        segment.set(LAYOUT, 0, value);
    }

    public static IntNativeImpl allocate(SegmentAllocator allocator) {
        return allocate(allocator, 0);
    }
}
