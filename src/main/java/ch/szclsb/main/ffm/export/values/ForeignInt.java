package ch.szclsb.main.ffm.export.values;

import ch.szclsb.main.ffm.export.HasAddress;

import java.lang.foreign.ValueLayout;

public interface ForeignInt extends HasAddress<ForeignInt> {
    ValueLayout.OfInt LAYOUT = ValueLayout.JAVA_INT;

    int getValue();

    void setValue(int value);
}
