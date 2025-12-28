package ch.szclsb.test.ffm.api;

import ch.szclsb.test.ffm.api.pointer.AddressPointer;
import ch.szclsb.test.ffm.api.structs.ForeignPoint;
import ch.szclsb.test.ffm.api.values.ForeignInt;
import ch.szclsb.test.ffm.api.vectors.ForeignVec4;

public interface ForeignFactory {
    <T extends HasAddress<?>> AddressPointer<T> allocatePointer();

    <T extends HasAddress<?>> AddressPointer<T> reference(Address<T> value);

    ForeignInt allocateInt();

    ForeignInt allocateInt(int value);

    ForeignInt readInt(Address<ForeignInt> address);

    ForeignPoint allocatePoint();

    ForeignPoint allocatePoint(int x, int y);

    ForeignPoint readPoint(Address<ForeignPoint> address);

    ForeignVec4 allocateVec4();

    ForeignVec4 allocateVec4(float x, float y, float z, float w);

    ForeignVec4 readVec4(Address<ForeignVec4> address);
}
