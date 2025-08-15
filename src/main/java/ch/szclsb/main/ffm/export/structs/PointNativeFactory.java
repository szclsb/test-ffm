package ch.szclsb.main.ffm.export.structs;

import ch.szclsb.main.ffm.export.Ref;

public interface PointNativeFactory {
    PointNative allocatePoint();

    PointNative allocatePoint(int x, int y);

    Ref<PointNative> createPointP();

    Ref<PointNative> createPointP(PointNative value);
}
