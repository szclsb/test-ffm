package ch.szclsb.main.ffm.impl;

import ch.szclsb.main.ffm.export.Address;
import ch.szclsb.main.ffm.export.AddressPointer;
import ch.szclsb.main.ffm.export.ForeignFactory;
import ch.szclsb.main.ffm.export.HasAddress;
import ch.szclsb.main.ffm.export.structs.ForeignPoint;
import ch.szclsb.main.ffm.export.values.ForeignInt;
import ch.szclsb.main.ffm.impl.pointer.AddressPointerImpl;
import ch.szclsb.main.ffm.impl.structs.ForeignPointImpl;
import ch.szclsb.main.ffm.impl.values.ForeignIntImpl;

import java.lang.foreign.Arena;

public class ForeignFactoryImpl implements ForeignFactory {
    private final Arena session;

    public ForeignFactoryImpl(Arena session) {
        this.session = session;
    }

    @Override
    public <T extends HasAddress<?>> AddressPointer<T> allocatePointer() {
        return AddressPointerImpl.allocate(session);
    }

    @Override
    public <T extends HasAddress<?>> AddressPointer<T> reference(Address<T> address) {
        AddressPointer<T> addressPointer = allocatePointer();
        addressPointer.reference(address);
        return addressPointer;
    }

    @Override
    public ForeignInt allocateInt() {
        return ForeignIntImpl.allocate(session);
    }

    @Override
    public ForeignInt allocateInt(int value) {
        var foreignInt = allocateInt();
        foreignInt.setValue(value);
        return foreignInt;
    }

    @Override
    public ForeignInt readInt(Address<ForeignInt> address) {
        return ForeignIntImpl.read(address);
    }

    @Override
    public ForeignPoint allocatePoint() {
        return ForeignPointImpl.allocate(session);
    }

    @Override
    public ForeignPoint allocatePoint(int x, int y) {
        var foreignPoint = allocatePoint();
        foreignPoint.setX(x);
        foreignPoint.setY(y);
        return foreignPoint;
    }

    @Override
    public ForeignPoint readPoint(Address<ForeignPoint> address) {
        return ForeignPointImpl.read(address);
    }
}
