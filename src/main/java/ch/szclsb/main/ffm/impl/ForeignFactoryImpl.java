package ch.szclsb.main.ffm.impl;

import ch.szclsb.main.ffm.export.AddressPointer;
import ch.szclsb.main.ffm.export.ForeignFactory;
import ch.szclsb.main.ffm.export.HasSegment;
import ch.szclsb.main.ffm.export.structs.ForeignPoint;
import ch.szclsb.main.ffm.export.values.ForeignInt;
import ch.szclsb.main.ffm.impl.pointer.AddressPointerImpl;
import ch.szclsb.main.ffm.impl.structs.ForeignPointImpl;
import ch.szclsb.main.ffm.impl.values.ForeignIntImpl;

import java.lang.foreign.Arena;
import java.util.HashMap;
import java.util.Map;

public class ForeignFactoryImpl implements ForeignFactory {
    private static final Map<Class<?>, AddressTarget<?>> addressTargetMap = collectAddressTargets();

    private static Map<Class<?>, AddressTarget<?>> collectAddressTargets() {
        Map<Class<?>, AddressTarget<?>> result = new HashMap<>();
        result.put(ForeignPoint.class, new AddressTarget<>(ForeignPoint.LAYOUT, ForeignPointImpl::new));
        return result;
    }

    private final Arena session;

    public ForeignFactoryImpl(Arena session) {
        this.session = session;
    }

    @SuppressWarnings("unchecked")
    private <R extends HasSegment> AddressTarget<R> getAddressConstructor(Class<R> refClass) {
        return (AddressTarget<R>) addressTargetMap.get(refClass);
    }

    @Override
    public <R extends HasSegment> AddressPointer<R> allocatePointer(Class<R> refClass) {
        return AddressPointerImpl.allocate(session, getAddressConstructor(refClass));
    }

    @Override
    public <R extends HasSegment> AddressPointer<R> createReference(Class<R> refClass, R refObject) {
        var addressPointer = this.allocatePointer(refClass);
        addressPointer.reference(refObject);
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
}
