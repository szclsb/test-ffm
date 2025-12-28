package ch.szclsb.test.ffm.impl.structs;

import ch.szclsb.test.ffm.api.Address;
import ch.szclsb.test.ffm.api.structs.ForeignPoint;
import ch.szclsb.test.ffm.impl.BaseSegment;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;

import static java.lang.foreign.ValueLayout.JAVA_INT;

public class ForeignPointImpl extends BaseSegment implements ForeignPoint {
    private ForeignPointImpl(MemorySegment memorySegment) {
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

    public static ForeignPoint allocate(SegmentAllocator allocator) {
        return new ForeignPointImpl(allocator.allocate(LAYOUT));
    }

    public static ForeignPoint read(Address<ForeignPoint> address) {
        return new ForeignPointImpl(address.getSegment().reinterpret(LAYOUT.byteSize()));
    }
}
