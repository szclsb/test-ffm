package ch.szclsb.test.ffm.impl;

import ch.szclsb.test.ffm.api.Address;
import ch.szclsb.test.ffm.api.pointer.AddressPointer;
import ch.szclsb.test.ffm.api.ForeignFactory;
import ch.szclsb.test.ffm.api.HasAddress;
import ch.szclsb.test.ffm.api.structs.ForeignPoint;
import ch.szclsb.test.ffm.api.values.ForeignInt;
import ch.szclsb.test.ffm.api.vectors.ForeignVec4;
import ch.szclsb.test.ffm.impl.pointer.AddressPointerImpl;
import ch.szclsb.test.ffm.impl.structs.ForeignPointImpl;
import ch.szclsb.test.ffm.impl.values.ForeignIntImpl;
import ch.szclsb.test.ffm.impl.vectors.ForeignVec4Impl;

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

    @Override
    public ForeignVec4 allocateVec4() {
        return ForeignVec4Impl.allocate(session);
    }

    @Override
    public ForeignVec4 allocateVec4(float x, float y, float z, float w) {
        var vec4 = ForeignVec4Impl.allocate(session);
        vec4.setValues(x, y, z, w);
        return vec4;
    }

    @Override
    public ForeignVec4 readVec4(Address<ForeignVec4> address) {
        return ForeignVec4Impl.read(address);
    }
}
