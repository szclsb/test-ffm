package ch.szclsb.test.ffm.api;

import ch.szclsb.test.ffm.api.pointer.ForeignPointer;
import ch.szclsb.test.ffm.api.structs.ForeignPoint;
import ch.szclsb.test.ffm.api.values.ForeignInt;
import ch.szclsb.test.ffm.api.vectors.ForeignVec4;

public interface ForeignFactory {
    <T extends ForeignObject> ForeignPointer<T> allocatePointer(TargetType<T> targetType);

//    <T extends ForeignObject> ForeignPointer<T> reference(Class<T> targetClass, T target);

    ForeignInt allocateInt();

    ForeignInt allocateInt(int value);

    ForeignPoint allocatePoint();

    ForeignPoint allocatePoint(int x, int y);

    ForeignVec4 allocateVec4();

    ForeignVec4 allocateVec4(float x, float y, float z, float w);
}
