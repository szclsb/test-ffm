package ch.szclsb.test.ffm.api.pointer;

import ch.szclsb.test.ffm.api.Address;
import ch.szclsb.test.ffm.api.HasAddress;
import ch.szclsb.test.ffm.api.HasSegment;

import java.lang.foreign.AddressLayout;

public interface AddressPointer<T extends HasAddress<?>> extends HasSegment {
    AddressLayout LAYOUT = AddressLayout.ADDRESS;

    Address<T> dereference();

    void reference(Address<T> address);
}
