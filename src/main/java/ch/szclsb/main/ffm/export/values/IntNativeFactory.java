package ch.szclsb.main.ffm.export.values;

import ch.szclsb.main.ffm.export.Ref;

public interface IntNativeFactory {
    IntNative allocateInt();

    IntNative allocateInt(int value);

    Ref<IntNative> createIntP();

    Ref<IntNative> createIntP(IntNative value);

    Ref<Ref<IntNative>> createIntPP();

    Ref<Ref<IntNative>> createIntPP(Ref<IntNative> value);
}
