package ch.szclsb.test.ffm.impl.pointer;

import ch.szclsb.test.ffm.api.Address;
import ch.szclsb.test.ffm.api.HasAddress;
import ch.szclsb.test.ffm.api.pointer.AddressPointer;
import ch.szclsb.test.ffm.impl.BaseSegment;

import java.lang.foreign.MemorySegment;

public class AddressPointerImpl<T extends HasAddress<?>> extends BaseSegment implements AddressPointer<T> {
    public AddressPointerImpl(MemorySegment memorySegment) {
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
}
