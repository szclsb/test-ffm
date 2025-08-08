package ch.szclsb.main.ffm;

import ch.szclsb.main.ffm.export.Api;
import ch.szclsb.main.ffm.export.Ref;
import ch.szclsb.main.ffm.export.structs.PointNative;
import ch.szclsb.main.ffm.export.values.IntNative;
import ch.szclsb.main.ffm.impl.ApiImpl;
import ch.szclsb.main.ffm.impl.Vector4;
import ch.szclsb.main.ffm.impl.pointer.AddressPointer;
import ch.szclsb.main.ffm.impl.pointer.SegmentPointer;
import ch.szclsb.main.ffm.impl.values.IntNativeImpl;
import ch.szclsb.main.ffm.impl.structs.PointNativeImpl;

import java.lang.foreign.Arena;
import java.lang.foreign.ValueLayout;

public class Main {
    public static void main(String[] args) throws Throwable {

        try(var session = Arena.ofShared()) {
            Api nativeMethodHandler = new ApiImpl(session);
            nativeMethodHandler.printHello();

            nativeMethodHandler.passHello();

            System.out.printf("%s%n", nativeMethodHandler.getHello());

            var voidSegment = nativeMethodHandler.allocateForeignInt(10);
            System.out.printf("%d%n", voidSegment.reinterpret(4).get(ValueLayout.JAVA_INT, 0));
            nativeMethodHandler.freeForeign(voidSegment);

            var x = nativeMethodHandler.addFloat(1.0f, 2.0f);
            System.out.printf("r%f%n", x);

            var a = new Vector4(session);
            var b = new Vector4(session);
            var r = new Vector4(session);

            a.setValues(1f, 2f, 3f, 4f);
            b.setValues(6f, 7f, 9f, 10f);

            nativeMethodHandler.vec4add(a, b, r);
            var result = r.toArray();

            System.out.printf("r[%.2f, %.2f, %.2f, %.2f]%n", result[0], result[1], result[2], result[3]);

            Ref<PointNative> p1a = SegmentPointer.of(PointNativeImpl.allocate(session));
            p1a.dereference().setX(1);
            p1a.dereference().setY(2);
            Ref<PointNative> p2a = SegmentPointer.of(PointNativeImpl.allocate(session));
            p2a.dereference().setX(3);
            p2a.dereference().setY(4);
            Ref<PointNative> p3a = SegmentPointer.of(PointNativeImpl.allocate(session));
            nativeMethodHandler.pointAddRef(p1a, p2a, p3a);
            System.out.printf("r{%d, %d}%n", p3a.dereference().getX(), p3a.dereference().getY());

            PointNative p1b = PointNativeImpl.allocate(session);
            p1b.setX(4);
            p1b.setY(3);
            PointNative p2b = PointNativeImpl.allocate(session);
            p2b.setX(1);
            p2b.setY(2);
            PointNative p3b = nativeMethodHandler.pointAdd(p1b, p2b);
            System.out.printf("r{%d, %d}%n", p3b.getX(), p3b.getY());

            var instance = nativeMethodHandler.createInstance(12, -15);
            nativeMethodHandler.useInstance(instance);
            nativeMethodHandler.destroyInstance(instance);

            var inc1 = nativeMethodHandler.incrementInt(32);
            System.out.printf("inc1 of 32 is %d%n", inc1);

            Ref<IntNative> inc2 = SegmentPointer.of(IntNativeImpl.allocate(session, 42));
            nativeMethodHandler.incrementPInt(inc2);
            System.out.printf("inc2 of 42 is %d%n", inc2.dereference().getValue());

            Ref<Ref<IntNative>> inc3 = AddressPointer.allocate(session,
                    address -> SegmentPointer.of(IntNativeImpl.ofAddress(address)),
                    SegmentPointer.of(IntNativeImpl.allocate(session, 52)));
            //inc3.reference(inc2);
            nativeMethodHandler.incrementPpInt(inc3);
            System.out.printf("inc3 of 52 is %d%n", inc3.dereference().dereference().getValue());
        }
    }
}
