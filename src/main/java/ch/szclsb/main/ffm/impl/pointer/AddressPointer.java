package ch.szclsb.main.ffm.impl.pointer;

import ch.szclsb.main.ffm.export.Ref;
import ch.szclsb.main.ffm.impl.BaseSegment;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.util.function.Function;

public class AddressPointer<T extends Ref<?>> extends BaseSegment implements Ref<T> {
    public static final ValueLayout.OfLong LAYOUT = ValueLayout.JAVA_LONG;
    private final Function<Long, T> deref;

    private AddressPointer(Function<Long, T> deref, MemorySegment memorySegment) {
        super(memorySegment);
        this.deref = deref;
    }

    @Override
    public T dereference() {
        return deref.apply(segment.get(LAYOUT, 0));
    }

    @Override
    public void reference(T value) {
        this.segment.set(LAYOUT, 0, value.getAddress().address());
    }

    public static <T extends Ref<?>> Ref<T> allocate(SegmentAllocator allocator, Function<Long, T> deref, T value) {
        var ref = new AddressPointer<>(deref, allocator.allocate(LAYOUT));
        ref.reference(value);
        return ref;
    }
}
