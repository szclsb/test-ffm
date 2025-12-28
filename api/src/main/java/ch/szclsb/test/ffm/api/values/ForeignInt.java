package ch.szclsb.test.ffm.api.values;

import ch.szclsb.test.ffm.api.HasAddress;

import java.lang.foreign.ValueLayout;

public interface ForeignInt extends HasAddress<ForeignInt> {
    ValueLayout.OfInt LAYOUT = ValueLayout.JAVA_INT;

    int getValue();

    void setValue(int value);
}
