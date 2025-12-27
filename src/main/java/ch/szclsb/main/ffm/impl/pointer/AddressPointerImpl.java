package ch.szclsb.main.ffm.impl.pointer;

import ch.szclsb.main.ffm.export.AddressPointer;
import ch.szclsb.main.ffm.export.HasSegment;
import ch.szclsb.main.ffm.impl.AddressTarget;
import ch.szclsb.main.ffm.impl.BaseSegment;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;

public class AddressPointerImpl<R extends HasSegment> extends BaseSegment implements AddressPointer<R> {
    private final AddressTarget<R> addressTarget;

    private AddressPointerImpl(MemorySegment memorySegment, AddressTarget<R> addressTarget) {
        super(memorySegment);
        this.addressTarget = addressTarget;
    }

    @Override
    public R dereference() {
        var targetSegment = segment.getAtIndex(LAYOUT.withTargetLayout(addressTarget.targetLayout()), 0);
        return addressTarget.constructor().apply(targetSegment);
    }

    @Override
    public void reference(R object) {
        this.segment.set(LAYOUT, 0, object.getSegment());
    }

    public static <R extends HasSegment> AddressPointerImpl<R> allocate(SegmentAllocator allocator,
                                                                        AddressTarget<R> addressConstructor) {
        return new AddressPointerImpl<>(allocator.allocate(LAYOUT), addressConstructor);
    }
}
