package ch.szclsb.main.ffm.impl.pointer;

import ch.szclsb.main.ffm.export.Segment;
import ch.szclsb.main.ffm.export.Ref;

import java.lang.foreign.MemorySegment;

public class SegmentPointer<T extends Segment> implements Ref<T> {
    private T reference;

    @Override
    public T dereference() {
        return reference;
    }

    @Override
    public void reference(T value) {
        this.reference = value;
    }

    @Override
    public MemorySegment getAddress() {
        return reference == null ? MemorySegment.NULL : reference.getAddress();
    }

    public static <T extends Segment> Ref<T> of(T value) {
        var ref = new SegmentPointer<T>();
        ref.reference(value);
        return ref;
    }
}
