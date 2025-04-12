package ch.szclsb.main.ffm.export.structs;

import ch.szclsb.main.ffm.export.HasSegment;

public interface PointNative extends HasSegment {
    int getX();

    void setX(int x);

    int getY();

    void setY(int y);
}
