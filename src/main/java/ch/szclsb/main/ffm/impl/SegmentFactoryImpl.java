package ch.szclsb.main.ffm.impl;

import ch.szclsb.main.ffm.export.*;
import ch.szclsb.main.ffm.export.structs.PointNative;
import ch.szclsb.main.ffm.export.structs.PointNativeFactory;
import ch.szclsb.main.ffm.export.values.IntNative;
import ch.szclsb.main.ffm.export.values.IntNativeFactory;
import ch.szclsb.main.ffm.impl.pointer.AddressPointer;
import ch.szclsb.main.ffm.impl.pointer.SegmentPointer;
import ch.szclsb.main.ffm.impl.structs.PointNativeImpl;
import ch.szclsb.main.ffm.impl.values.IntNativeImpl;

import java.lang.foreign.SegmentAllocator;
import java.util.Map;
import java.util.function.Function;

public class SegmentFactoryImpl implements SegmentFactory {
    private final SegmentAllocator allocator;
    private final Map<Class<? extends Pointer>, Function<Long, ?>> drefMap;

    public SegmentFactoryImpl(SegmentAllocator allocator) {
        this.allocator = allocator;
        this.drefMap = Map.of(
                IntNativeImpl.class, IntNativeImpl::ofAddress,
                PointNativeImpl.class, PointNativeImpl::ofAddress
        );
    }

//    @SuppressWarnings("unchecked")
//    private <T extends Pointer> Function<Long, T> getDrefFunction(Class<T> tClass) {
//        return (Function<Long, T>) drefMap.get(tClass);
//    }
//
//    @SuppressWarnings("unchecked")
//    private static <T> Class<T> getTClass(T value) {
//        return (Class<T>) value.getClass();
//    }
//
//    @Override
//    public <T extends Ref<?>> Ref<T> reference(T ref) {
//        var innerClass = ref.getRefClass();
//        Function<Long, T> dref = address -> {
//            throw new IllegalArgumentException("invalid dreference");
//        };
//        if (ref instanceof SegmentPointer<?> segmentPointer) {
//            var vref = getDrefFunction(segmentPointer.getRefClass());
//            dref = address -> {
//                var value = vref.apply(address);
//                var p = new SegmentPointer<>(segmentPointer.getRefClass());
//                p.reference(value);
//                return p;
//            };
//        }
//        return AddressPointer.allocate(allocator, getTClass(ref), dref);
//    }
//
//    @Override
//    public <T extends Segment> Ref<T> createSegmentPointer(Class<T> rClass) {
//        return new SegmentPointer<>(rClass);
//    }
//
//    @Override
//    public <T extends Segment> Ref<T> reference(T value) {
//        var ref = new SegmentPointer<>(getTClass(value));
//        ref.reference(value);
//        return ref;
//    }

    @Override
    public IntNative allocateInt() {
        return IntNativeImpl.allocate(allocator);
    }

    @Override
    public IntNative allocateInt(int value) {
        return IntNativeImpl.allocate(allocator, value);
    }

    @Override
    public Ref<IntNative> createIntP() {
        return new SegmentPointer<>(null);
    }

    @Override
    public Ref<IntNative> createIntP(IntNative value) {
        var p = new SegmentPointer<IntNative>(null);
        p.reference(value);
        return p;
    }

    @Override
    public Ref<Ref<IntNative>> createIntPP() {
        return AddressPointer.allocate(allocator, address -> createIntP(IntNativeImpl.ofAddress(address)));
    }

    @Override
    public Ref<Ref<IntNative>> createIntPP(Ref<IntNative> ref) {
        var pp = createIntPP();
        pp.reference(ref);
        return pp;
    }

    @Override
    public PointNative allocatePoint() {
        return PointNativeImpl.allocate(allocator);
    }

    @Override
    public PointNative allocatePoint(int x, int y) {
        return PointNativeImpl.allocate(allocator, x, y);
    }

    @Override
    public Ref<PointNative> createPointP() {
        return new SegmentPointer<>(null);
    }

    @Override
    public Ref<PointNative> createPointP(PointNative value) {
        var p = new SegmentPointer<PointNative>(null);
        p.reference(value);
        return p;
    }
}