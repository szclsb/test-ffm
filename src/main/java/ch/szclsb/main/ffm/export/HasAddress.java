package ch.szclsb.main.ffm.export;

public interface HasAddress<T extends HasAddress<?>> extends HasSegment {
    default Address<T> getAddress() {
        return () -> getSegment().reinterpret(0);
    }
}
