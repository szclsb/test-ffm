package ch.szclsb.main.ffm.export;

import java.lang.foreign.AddressLayout;

public interface AddressPointer<R extends HasSegment> extends HasSegment {
    AddressLayout LAYOUT = AddressLayout.ADDRESS;

    R dereference();

    void reference(R object);
}
