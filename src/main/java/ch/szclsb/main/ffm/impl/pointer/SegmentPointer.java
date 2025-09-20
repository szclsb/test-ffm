package ch.szclsb.main.ffm.impl.pointer;

import ch.szclsb.main.ffm.export.Native;
import ch.szclsb.main.ffm.export.Address;

import java.lang.foreign.MemorySegment;

public class SegmentPointer<T extends Native> implements Address<T> {
    private M

    private SegmentPointer() {
        ;
    }

    @Override
    public T dereference() {
        return null;
    }

    @Override
    public void reference(T value) {
        this.segment = value == null ? MemorySegment.NULL : value.getSegment().reinterpret(0);
    }
}
