package ch.szclsb.main.ffm;

import ch.szclsb.main.ffm.export.Api;
import ch.szclsb.main.ffm.export.ForeignFactory;
import ch.szclsb.main.ffm.impl.ApiImpl;
import ch.szclsb.main.ffm.impl.ForeignFactoryImpl;
import ch.szclsb.main.ffm.impl.Vector4;

import java.lang.foreign.Arena;
import java.lang.foreign.ValueLayout;

public class Main {
    public static void main(String[] args) throws Throwable {

        try(var session = Arena.ofShared()) {
            Api nativeMethodHandler = new ApiImpl(session);
            ForeignFactory foreignFactory = new ForeignFactoryImpl(session);

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

            var p1a = foreignFactory.allocatePoint(1, 2);
            var p2a = foreignFactory.allocatePoint(3, 4);
            var p3a = foreignFactory.allocatePoint();
            nativeMethodHandler.pointAddRef(p1a.getAddress(), p2a.getAddress(), p3a.getAddress());
            System.out.printf("r{%d, %d}%n", p3a.getX(), p3a.getY());

            var p1b = foreignFactory.allocatePoint();
            p1b.setX(4);
            p1b.setY(3);
            var p2b = foreignFactory.allocatePoint();
            p2b.setX(1);
            p2b.setY(2);
            var p3b = nativeMethodHandler.pointAdd(p1b, p2b);
            System.out.printf("r{%d, %d}%n", p3b.getX(), p3b.getY());

            var instance = nativeMethodHandler.createInstance(12, -15);
            nativeMethodHandler.useInstance(instance);
            nativeMethodHandler.destroyInstance(instance);

            var inc1 = nativeMethodHandler.incrementInt(32);
            System.out.printf("inc1 of 32 is %d%n", inc1);

            var inc2 = foreignFactory.allocateInt(42);
            nativeMethodHandler.incrementPInt(inc2);
            System.out.printf("inc2 of 42 is %d%n", inc2.getValue());

            var inc3 = foreignFactory.createReference(foreignFactory.allocateInt(52).getAddress());
            //inc3.reference(inc2);
            nativeMethodHandler.incrementPpInt(inc3);
            System.out.printf("inc3 of 52 is %d%n", foreignFactory.readInt(inc3.dereference()).getValue());
        }
    }
}
