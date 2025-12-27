package ch.szclsb.main.ffm.export.values;

import ch.szclsb.main.ffm.export.HasSegment;

import java.lang.foreign.ValueLayout;

public interface ForeignInt extends HasSegment {
    ValueLayout.OfInt LAYOUT = ValueLayout.JAVA_INT;

    int getValue();

    void setValue(int value);
}
