package ch.szclsb.test.ffm.impl.values;

import ch.szclsb.test.ffm.api.Address;
import ch.szclsb.test.ffm.api.values.ForeignInt;
import ch.szclsb.test.ffm.impl.BaseSegment;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;

/**
 * Handles int pointer
 */
public class ForeignIntImpl extends BaseSegment implements ForeignInt {
    public ForeignIntImpl(MemorySegment memorySegment) {
        super(memorySegment);
    }

    @Override
    public int getValue() {
        return segment.get(LAYOUT, 0);
    }

    @Override
    public void setValue(int value) {
        segment.set(LAYOUT, 0, value);
    }
}
