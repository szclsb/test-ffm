package ch.szclsb.test.ffm.api.vectors;

import ch.szclsb.test.ffm.api.HasAddress;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.ValueLayout;

public interface ForeignVec4 extends HasAddress<ForeignVec4> {
    ValueLayout.OfFloat VALUE_LAYOUT = ValueLayout.JAVA_FLOAT;
    MemoryLayout LAYOUT = MemoryLayout.sequenceLayout(4, VALUE_LAYOUT);

    float getX();

    void setX(float x);

    float getY();

    void setY(float y);

    float getZ();

    void setZ(float z);

    float getW();

    void setW(float w);

    void setValues(float x, float y, float z, float w);

    float[] toArray();
}
