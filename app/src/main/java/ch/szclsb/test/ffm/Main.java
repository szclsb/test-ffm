package ch.szclsb.test.ffm;

import java.lang.foreign.Arena;
import java.lang.foreign.ValueLayout;

public class Main {
    static void main(String[] args) throws Throwable {
        try (var spiLoader = new SpiLoader();
             var session = Arena.ofShared()) {
            var provider = spiLoader.providers().getFirst();
            var api = provider.getApi(session);
            var factory = provider.getFactory(session);

            api.printHello();

            api.passHello();

            System.out.printf("%s%n", api.getHello());

            var voidSegment = api.allocateForeignInt(10);
            System.out.printf("%d%n", voidSegment.reinterpret(4).get(ValueLayout.JAVA_INT, 0));
            api.freeForeign(voidSegment);

            var x = api.addFloat(1.0f, 2.0f);
            System.out.printf("r%f%n", x);

//            var a = new Vector4(session);
//            var b = new Vector4(session);
//            var r = new Vector4(session);
//
//            a.setValues(1f, 2f, 3f, 4f);
//            b.setValues(6f, 7f, 9f, 10f);
//
//            nativeMethodHandler.vec4add(a, b, r);
//            var result = r.toArray();
//
//            System.out.printf("r[%.2f, %.2f, %.2f, %.2f]%n", result[0], result[1], result[2], result[3]);

            var p1a = factory.allocatePoint(1, 2);
            var p2a = factory.allocatePoint(3, 4);
            var p3a = factory.allocatePoint();
            api.pointAddRef(p1a.getAddress(), p2a.getAddress(), p3a.getAddress());
            System.out.printf("r{%d, %d}%n", p3a.getX(), p3a.getY());

            var p1b = factory.allocatePoint();
            p1b.setX(4);
            p1b.setY(3);
            var p2b = factory.allocatePoint();
            p2b.setX(1);
            p2b.setY(2);
            var p3b = api.pointAdd(p1b, p2b);
            System.out.printf("r{%d, %d}%n", p3b.getX(), p3b.getY());

            var instance = api.createInstance(12, -15);
            api.useInstance(instance);
            api.destroyInstance(instance);

            var inc1 = api.incrementInt(32);
            System.out.printf("inc1 of 32 is %d%n", inc1);

            var inc2 = factory.allocateInt(42);
            api.incrementPInt(inc2);
            System.out.printf("inc2 of 42 is %d%n", inc2.getValue());

            var inc3 = factory.reference(factory.allocateInt(52).getAddress());
            //inc3.reference(inc2);
            api.incrementPpInt(inc3);
            System.out.printf("inc3 of 52 is %d%n", factory.readInt(inc3.dereference()).getValue());
        }
    }
}
