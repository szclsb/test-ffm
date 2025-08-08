package ch.szclsb.main.ffm.export.structs;

import ch.szclsb.main.ffm.export.Segment;

public interface PointNative extends Segment {
    int getX();

    void setX(int x);

    int getY();

    void setY(int y);
}
