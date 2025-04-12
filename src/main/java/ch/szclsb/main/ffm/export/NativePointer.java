package ch.szclsb.main.ffm.export;

public interface NativePointer<T extends Native> extends Native {
    T getReference();
}
