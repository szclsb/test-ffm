package ch.szclsb.main.ffm.export.values;

import ch.szclsb.main.ffm.export.Native;

public interface LongNative extends Native {
    long getValue();

    void setValue(long value);
}
