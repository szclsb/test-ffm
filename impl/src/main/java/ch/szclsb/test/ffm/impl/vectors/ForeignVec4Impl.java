package ch.szclsb.test.ffm.impl.vectors;

import ch.szclsb.test.ffm.api.Address;
import ch.szclsb.test.ffm.api.vectors.ForeignVec4;
import ch.szclsb.test.ffm.impl.BaseSegment;

import java.lang.foreign.*;

public class ForeignVec4Impl extends BaseSegment implements ForeignVec4 {
    private ForeignVec4Impl(MemorySegment memorySegment) {
        super(memorySegment);
    }

    @Override
    public float getX() {
        return segment.get(VALUE_LAYOUT, 0);
    }

    @Override
    public void setX(float x) {
        segment.set(VALUE_LAYOUT, 0, x);
    }

    @Override
    public float getY() {
        return segment.get(VALUE_LAYOUT, 4);
    }

    @Override
    public void setY(float y) {
        segment.set(VALUE_LAYOUT, 4, y);
    }

    @Override
    public float getZ() {
        return segment.get(VALUE_LAYOUT, 8);
    }

    @Override
    public void setZ(float z) {
        segment.set(VALUE_LAYOUT, 8, z);
    }

    @Override
    public float getW() {
        return segment.get(VALUE_LAYOUT, 12);
    }

    @Override
    public void setW(float w) {
        segment.set(VALUE_LAYOUT, 12, w);
    }

    @Override
    public void setValues(float x, float y, float z, float w) {
        setX(x);
        setY(y);
        setZ(z);
        setW(w);
    }

    @Override
    public float[] toArray() {
        return segment.toArray(VALUE_LAYOUT);
    }

    public static ForeignVec4 allocate(SegmentAllocator allocator) {
        return new ForeignVec4Impl(allocator.allocate(LAYOUT));
    }

    public static ForeignVec4 read(Address<ForeignVec4> address) {
        return new ForeignVec4Impl(address.getSegment().reinterpret(LAYOUT.byteSize()));
    }

}
