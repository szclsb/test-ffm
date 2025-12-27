package ch.szclsb.main.ffm.impl;

import ch.szclsb.main.ffm.export.HasSegment;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.util.function.Function;

public record AddressTarget<T extends HasSegment>(
        MemoryLayout targetLayout,
        Function<MemorySegment, T> constructor) {
}
