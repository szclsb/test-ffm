package ch.szclsb.main.ffm.export;

public interface NativePointer<T extends Native> extends HasSegment {
    T getReference();
}
