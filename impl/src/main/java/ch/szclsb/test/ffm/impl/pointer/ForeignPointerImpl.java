package ch.szclsb.test.ffm.impl.pointer;

import ch.szclsb.test.ffm.api.ForeignObject;
import ch.szclsb.test.ffm.api.pointer.ForeignPointer;
import ch.szclsb.test.ffm.impl.BaseSegment;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.util.function.Function;

public class ForeignPointerImpl<T extends ForeignObject> extends BaseSegment implements ForeignPointer<T> {
    private final AddressLayout addressLayout;
    private final Function<MemorySegment, ? extends T> dereferenceFunction;

    public ForeignPointerImpl(MemorySegment memorySegment,
                              MemoryLayout targetLayout,
                              Function<MemorySegment, ? extends T> dereferenceFunction) {
        super(memorySegment);
        this.addressLayout = LAYOUT.withTargetLayout(targetLayout);
        this.dereferenceFunction = dereferenceFunction;
    }

    @Override
    public T dereference() {
        var referencedSegment = segment.getAtIndex(addressLayout, 0);
        return referencedSegment.address() == 0L ? null : this.dereferenceFunction.apply(referencedSegment);
    }

    @Override
    public void reference(T ref) {
        this.segment.set(addressLayout, 0, ref == null ? MemorySegment.NULL : ref.getSegment());
    }
}
