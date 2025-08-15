package ch.szclsb.main.ffm.export.values;

import ch.szclsb.main.ffm.export.Segment;
import java.lang.foreign.ValueLayout;

import static java.lang.foreign.ValueLayout.JAVA_INT;

public interface IntNative extends Segment {
    ValueLayout.OfInt LAYOUT = JAVA_INT;

    int getValue();

    void setValue(int value);
}
