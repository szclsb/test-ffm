package ch.szclsb.main.ffm.impl.pointer;

import ch.szclsb.main.ffm.export.Segment;
import ch.szclsb.main.ffm.export.Ref;

import java.lang.foreign.MemorySegment;

public class SegmentPointer<T extends Segment> implements Ref<T> {
//    private final Class<T> rClass;
    private T reference;

    public SegmentPointer(Class<T> rClass) {
//        this.rClass = rClass;
    }

//    @Override
//    public Class<T> getRefClass() {
//        return rClass;
//    }

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
}
