package ch.szclsb.test.ffm.api.pointer;

import ch.szclsb.test.ffm.api.ForeignObject;

import java.lang.foreign.AddressLayout;


public interface ForeignPointer<T extends ForeignObject> extends ForeignObject {
    AddressLayout LAYOUT = AddressLayout.ADDRESS;

    T dereference();

    void reference(T address);
}
