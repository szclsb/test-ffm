package ch.szclsb.main.ffm.export;

public interface Ref<T extends Pointer> extends Pointer {
//    Class<T> getRefClass();

    T dereference();

    void reference(T value);
}
