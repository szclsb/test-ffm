package ch.szclsb.main.ffm.export.structs;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.StructLayout;

import static java.lang.foreign.ValueLayout.JAVA_INT;

public interface ForeignPoint extends HasAddress<ForeignPoint> {
    StructLayout LAYOUT = MemoryLayout.structLayout(
            JAVA_INT.withName("x"),
            JAVA_INT.withName("y")
    ).withName("Point");

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);
}
