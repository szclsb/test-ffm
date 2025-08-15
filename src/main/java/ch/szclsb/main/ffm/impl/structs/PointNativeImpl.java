package ch.szclsb.main.ffm.impl.structs;

import ch.szclsb.main.ffm.export.structs.PointNative;
import ch.szclsb.main.ffm.impl.BaseSegment;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.StructLayout;

import static java.lang.foreign.ValueLayout.JAVA_INT;

public class PointNativeImpl extends BaseSegment implements PointNative  {
    private PointNativeImpl(MemorySegment memorySegment) {
        super(memorySegment);
    }

    public int getX() {
        return segment.get(JAVA_INT, 0);
    }

    public void setX(int x) {
        segment.set(JAVA_INT, 0, x);
    }

    public int getY() {
        return segment.get(JAVA_INT, 4);
    }

    public void setY(int y) {
        segment.set(JAVA_INT, 4, y);
    }

    public static PointNativeImpl allocate(SegmentAllocator allocator) {
        return allocate(allocator, 0, 0);
    }

    public static PointNativeImpl allocate(SegmentAllocator allocator, int x, int y) {
        var point = new PointNativeImpl(allocator.allocate(LAYOUT));
        point.setX(x);
        point.setY(y);
        return point;
    }

    public static PointNativeImpl ofAddress(long address) {
        return new PointNativeImpl(MemorySegment.ofAddress(address).reinterpret(LAYOUT.byteSize()));
    }

    public static PointNativeImpl ofSegment(MemorySegment memorySegment) {
        if (memorySegment == null || memorySegment.byteSize() != LAYOUT.byteSize()) {
            throw new IllegalArgumentException("segment has invalid layout. given size: %d, expected size: %d".formatted(memorySegment == null ? -1 : memorySegment.byteSize(), LAYOUT.byteSize()));
        }
        return new PointNativeImpl(memorySegment);
    }
}
