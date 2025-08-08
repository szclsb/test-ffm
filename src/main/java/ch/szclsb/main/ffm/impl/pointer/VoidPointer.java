package ch.szclsb.main.ffm.impl.pointer;

import ch.szclsb.main.ffm.export.Pointer;

import java.lang.foreign.MemorySegment;

public record VoidPointer(MemorySegment segment) implements Pointer {
    public VoidPointer() {
        this(null);
    }

    public VoidPointer(MemorySegment segment) {
        var mem = segment == null ? MemorySegment.NULL : segment;
        if (mem.byteSize() > 0) {
            throw new IllegalArgumentException("segment must be a pointer with byte size 0");
        }
        this.segment = mem;
    }

    @Override
    public MemorySegment getAddress() {
        return segment;
    }
}
