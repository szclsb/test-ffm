package ch.szclsb.main.ffm.export;

import ch.szclsb.main.ffm.export.structs.ForeignPoint;
import ch.szclsb.main.ffm.export.values.ForeignInt;

public interface ForeignFactory {
    <R extends HasSegment> AddressPointer<R> allocatePointer(Class<R> refClass);

    <R extends HasSegment> AddressPointer<R> createReference(Class<R> refClass, R refObject);

    ForeignInt allocateInt();

    ForeignInt allocateInt(int value);

    ForeignPoint allocatePoint();

    ForeignPoint allocatePoint(int x, int y);
}
