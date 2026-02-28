package ch.szclsb.test.ffm;

import ch.szclsb.test.ffm.api.TargetType;
import ch.szclsb.test.ffm.api.pointer.ForeignPointer;
import ch.szclsb.test.ffm.api.values.ForeignInt;

import java.lang.foreign.Arena;
import java.lang.foreign.ValueLayout;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final Path API_DLL_PATH = Paths.get(System.getProperty("user.dir"),
            "build-native", "Debug", "ffm.dll");

    static void main(String[] args) throws Throwable {
        try(var serviceProviderManager = new ServiceProviderManager()) {
            var availableServiceProviders = serviceProviderManager.loadServiceProviders();
            var serviceProvider = availableServiceProviders.getFirst();

            testFfm(serviceProvider);  // serviceProviderManager may be closed after this method
        }
    }

    private static void testFfm(FfmServiceProvider serviceProvider) throws Throwable {
        try (var session = Arena.ofShared()) {
            var factory = serviceProvider.getFactory(session);
            var api = serviceProvider.getApi(API_DLL_PATH, session, factory);

            api.printHello();

            api.passHello();

            System.out.printf("%s%n", api.getHello());

            var voidSegment = api.allocateForeignInt(10);
            System.out.printf("%d%n", voidSegment.reinterpret(4).get(ValueLayout.JAVA_INT, 0));
            api.freeForeign(voidSegment);

            var x = api.addFloat(1.0f, 2.0f);
            System.out.printf("r%f%n", x);

            var a = factory.allocateVec4();
            var b = factory.allocateVec4();
            var r = factory.allocateVec4();

            a.setValues(1f, 2f, 3f, 4f);
            b.setValues(6f, 7f, 9f, 10f);

            api.vec4add(a, b, r);
            var result = r.toArray();

            System.out.printf("r[%.2f, %.2f, %.2f, %.2f]%n", result[0], result[1], result[2], result[3]);

            var p1a = factory.allocatePoint(1, 2);
            var p2a = factory.allocatePoint(3, 4);
            var p3a = factory.allocatePoint();
            api.pointAddRef(p1a, p2a, p3a);
            System.out.printf("r{%d, %d}%n", p3a.getX(), p3a.getY());

            var p1b = factory.allocatePoint();
            p1b.setX(4);
            p1b.setY(3);
            var p2b = factory.allocatePoint(1, 2);
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

            var inc3 = factory.allocatePointer(new TargetType<ForeignInt>() {});
            inc3.reference(factory.allocateInt(52));
            api.incrementPpInt(inc3);
            System.out.printf("inc3 of 52 is %d%n", inc3.dereference().getValue());

            var inc4 = factory.allocatePointer(new TargetType<ForeignPointer<ForeignInt>>() {});
            var inc4_1 = factory.allocatePointer(new TargetType<ForeignInt>() {});
            var inc4_2 = factory.allocateInt(53);
            inc4.reference(inc4_1);
            inc4_1.reference(inc4_2);
            api.incrementPppInt(inc4);

            var x1 = inc4.dereference();
            var x2 = x1.dereference();
            System.out.printf("inc4 of 53 is %d%n", x2.getValue());
        }
    }
}
