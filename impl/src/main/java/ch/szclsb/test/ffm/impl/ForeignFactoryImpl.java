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
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

public class ForeignFactoryImpl implements ForeignFactory {
    private final Arena session;

    public ForeignFactoryImpl(Arena session) {
        this.session = session;
    }

    private MemorySegment allocate(MemoryLayout layout) {
        return session.allocate(layout);
    }

    private MemorySegment reinterpret(Address<?> address, MemoryLayout layout) {
        return address.getSegment().reinterpret(layout.byteSize());
    }

    @Override
    public <T extends HasAddress<?>> AddressPointer<T> allocatePointer() {
        var segment = allocate(AddressPointer.LAYOUT);
        return new AddressPointerImpl<>(segment);
    }

    @Override
    public <T extends HasAddress<?>> AddressPointer<T> reference(Address<T> address) {
        AddressPointer<T> addressPointer = allocatePointer();
        addressPointer.reference(address);
        return addressPointer;
    }

    @Override
    public ForeignInt allocateInt() {
        var segment = allocate(ForeignInt.LAYOUT);
        return new ForeignIntImpl(segment);
    }

    @Override
    public ForeignInt allocateInt(int value) {
        var foreignInt = allocateInt();
        foreignInt.setValue(value);
        return foreignInt;
    }

    @Override
    public ForeignInt readInt(Address<ForeignInt> address) {
        var segment = reinterpret(address, ForeignInt.LAYOUT);
        return new ForeignIntImpl(segment);
    }

    @Override
    public ForeignPoint allocatePoint() {
        var segment = allocate(ForeignPoint.LAYOUT);
        return new ForeignPointImpl(segment);
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
        var segment = reinterpret(address, ForeignPoint.LAYOUT);
        return new ForeignPointImpl(segment);
    }

    @Override
    public ForeignVec4 allocateVec4() {
        var segment = allocate(ForeignVec4.LAYOUT);
        return new ForeignVec4Impl(segment);
    }

    @Override
    public ForeignVec4 allocateVec4(float x, float y, float z, float w) {
        var vec4 = allocateVec4();
        vec4.setValues(x, y, z, w);
        return vec4;
    }

    @Override
    public ForeignVec4 readVec4(Address<ForeignVec4> address) {
        var segment = reinterpret(address, ForeignVec4.LAYOUT);
        return new ForeignVec4Impl(segment);
    }
}
