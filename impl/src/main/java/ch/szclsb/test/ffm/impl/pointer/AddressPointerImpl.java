package ch.szclsb.test.ffm.impl.pointer;

import ch.szclsb.test.ffm.api.Address;
import ch.szclsb.test.ffm.api.AddressPointer;
import ch.szclsb.test.ffm.api.HasAddress;
import ch.szclsb.test.ffm.impl.BaseSegment;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;

public class AddressPointerImpl<T extends HasAddress<?>> extends BaseSegment implements AddressPointer<T> {
    private AddressPointerImpl(MemorySegment memorySegment) {
        super(memorySegment);
    }

    @Override
    public Address<T> dereference() {
        return () -> segment.getAtIndex(LAYOUT, 0);
    }

    @Override
    public void reference(Address<T> ref) {
        this.segment.set(LAYOUT, 0, ref.getSegment());
    }

    public static <T extends HasAddress<?>> AddressPointerImpl<T> allocate(SegmentAllocator allocator) {
        return new AddressPointerImpl<>(allocator.allocate(LAYOUT));
    }
}
