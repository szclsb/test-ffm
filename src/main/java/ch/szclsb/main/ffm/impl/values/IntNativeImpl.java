package ch.szclsb.main.ffm.impl.values;

import ch.szclsb.main.ffm.export.values.IntNative;
import ch.szclsb.main.ffm.impl.BaseSegment;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;

/**
 * Handles int pointer
 */
public class IntNativeImpl extends BaseSegment implements IntNative {
    public static ValueLayout.OfInt LAYOUT = ValueLayout.JAVA_INT;

    private IntNativeImpl(MemorySegment memorySegment) {
        super(memorySegment);
    }

    @Override
    public int getValue() {
        return segment.get(LAYOUT, 0);
    }

    @Override
    public void setValue(int value) {
        segment.set(LAYOUT, 0, value);
    }

    public static IntNativeImpl allocate(SegmentAllocator allocator) {
        return allocate(allocator, 0);
    }

    public static IntNativeImpl allocate(SegmentAllocator allocator, int value) {
        var intNative = new IntNativeImpl(allocator.allocate(LAYOUT));
        intNative.setValue(value);
        return intNative;
    }

    public static IntNativeImpl ofAddress(long address) {
        return new IntNativeImpl(MemorySegment.ofAddress(address).reinterpret(LAYOUT.byteSize()));
    }
}
