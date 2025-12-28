package ch.szclsb.test.ffm.api;

import java.lang.foreign.AddressLayout;

public interface AddressPointer<T extends HasAddress<?>> extends HasSegment {
    AddressLayout LAYOUT = AddressLayout.ADDRESS;

    Address<T> dereference();

    void reference(Address<T> address);
}
