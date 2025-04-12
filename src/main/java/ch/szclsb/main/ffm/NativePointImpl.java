package ch.szclsb.main.ffm;

import ch.szclsb.main.ffm.export.NativePointer;
import ch.szclsb.main.ffm.export.structs.PointNative;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.StructLayout;

import static java.lang.foreign.ValueLayout.JAVA_INT;

public class NativePointImpl implements PointNative, NativePointer<PointNative> {
    public static final StructLayout LAYOUT = MemoryLayout.structLayout(
            JAVA_INT.withName("x"),
            JAVA_INT.withName("y")
    ).withName("Point");

    private final MemorySegment segment;

    public NativePointImpl(SegmentAllocator allocator) {
        this.segment = allocator.allocate(LAYOUT);
    }

    public NativePointImpl(MemorySegment segment) {
        this.segment = segment;
    }

    public MemorySegment getSegment() {
        return segment.asReadOnly();
    }

    @Override
    public PointNative getReference() {
        return this;
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
}
