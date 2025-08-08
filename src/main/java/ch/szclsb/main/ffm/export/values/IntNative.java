package ch.szclsb.main.ffm.export.values;

import ch.szclsb.main.ffm.export.Segment;

public interface IntNative extends Segment {
    int getValue();

    void setValue(int value);
}
