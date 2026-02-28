package ch.szclsb.test.ffm.api.values;

import ch.szclsb.test.ffm.api.ForeignObject;

import java.lang.foreign.ValueLayout;

public interface ForeignInt extends ForeignObject {
    ValueLayout.OfInt LAYOUT = ValueLayout.JAVA_INT;

    int getValue();

    void setValue(int value);
}
