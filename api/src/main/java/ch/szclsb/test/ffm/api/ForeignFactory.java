package ch.szclsb.test.ffm.api;

import ch.szclsb.test.ffm.api.structs.ForeignPoint;
import ch.szclsb.test.ffm.api.values.ForeignInt;

public interface ForeignFactory {
    <T extends HasAddress<?>> AddressPointer<T> allocatePointer();

    <T extends HasAddress<?>> AddressPointer<T> reference(Address<T> value);

    ForeignInt allocateInt();

    ForeignInt allocateInt(int value);

    ForeignInt readInt(Address<ForeignInt> address);

    ForeignPoint allocatePoint();

    ForeignPoint allocatePoint(int x, int y);

    ForeignPoint readPoint(Address<ForeignPoint> address);
}
