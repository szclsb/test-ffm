package ch.szclsb.main.ffm.impl.structs;

import ch.szclsb.main.ffm.export.Address;
import ch.szclsb.main.ffm.export.structs.Point;
import ch.szclsb.main.ffm.impl.BaseSegment;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;

import static java.lang.foreign.ValueLayout.JAVA_INT;

public class PointImpl extends BaseSegment implements Point {
    private PointImpl(MemorySegment memorySegment) {
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

    public Address<Point> getAddress() {
        return () -> segment.reinterpret(0).asReadOnly();
    }

    public static PointImpl allocate(SegmentAllocator allocator) {
        return allocate(allocator, 0, 0);
    }

    public static PointImpl allocate(SegmentAllocator allocator, int x, int y) {
        var point = new PointImpl(allocator.allocate(LAYOUT));
        point.setX(x);
        point.setY(y);
        return point;
    }

    public static PointImpl read(Address<Point> address) {
        address.getSegment().
        var point = new PointImpl(allocator.allocate(LAYOUT));
        point.setX(x);
        point.setY(y);
        return point;
    }
}
