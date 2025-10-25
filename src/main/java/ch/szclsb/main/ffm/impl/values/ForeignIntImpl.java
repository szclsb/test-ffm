package ch.szclsb.main.ffm.impl.values;

import ch.szclsb.main.ffm.export.Address;
import ch.szclsb.main.ffm.export.values.ForeignInt;
import ch.szclsb.main.ffm.export.structs.ForeignPoint;
import ch.szclsb.main.ffm.impl.BaseSegment;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;

/**
 * Handles int pointer
 */
public class ForeignIntImpl extends BaseSegment implements ForeignInt {
    private ForeignIntImpl(MemorySegment memorySegment) {
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

    @Override
    public Address<ForeignInt> getAddress() {
        return () -> segment.reinterpret(0);
    }

    public static ForeignInt allocate(SegmentAllocator allocator) {
        return new ForeignIntImpl(allocator.allocate(LAYOUT));
    }

    public static ForeignInt read(Address<ForeignInt> address) {
        return new ForeignIntImpl(address.getSegment().reinterpret(LAYOUT.byteSize()));
    }
}
