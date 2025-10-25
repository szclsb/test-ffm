package ch.szclsb.main.ffm.export;

import ch.szclsb.main.ffm.export.structs.ForeignPoint;
import ch.szclsb.main.ffm.export.values.ForeignInt;

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
