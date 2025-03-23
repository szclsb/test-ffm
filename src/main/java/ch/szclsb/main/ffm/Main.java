package ch.szclsb.main.ffm;

import java.lang.foreign.Arena;

public class Main {
    public static void main(String[] args) throws Throwable {

        try(var session = Arena.ofShared()) {
            var nativeMethodHandler = new NativeMethodHandlerImpl(session);
            nativeMethodHandler.printHello();

            nativeMethodHandler.passHello();

            var a = new Vector4(session);
            var b = new Vector4(session);
            var r = new Vector4(session);

            a.setValues(1f, 2f, 3f, 4f);
            b.setValues(6f, 7f, 9f, 10f);

            nativeMethodHandler.vec4add(a, b, r);
            var result = r.toArray();

            System.out.printf("r[%.2f, %.2f, %.2f, %.2f]%n", result[0], result[1], result[2], result[3]);

            var p1a = new PointNative(session);
            p1a.setX(1);
            p1a.setY(2);
            var p2a = new PointNative(session);
            p2a.setX(3);
            p2a.setY(4);
            var p3a = new PointNative(session);
            nativeMethodHandler.pointAddRef(p1a, p2a, p3a);
            System.out.printf("r{%d, %d}%n", p3a.getX(), p3a.getY());

            var p1b = new PointNative(session);
            p1b.setX(4);
            p1b.setY(3);
            var p2b = new PointNative(session);
            p2b.setX(1);
            p2b.setY(2);
            var p3b = nativeMethodHandler.pointAdd(p1b, p2b);
            System.out.printf("r{%d, %d}%n", p3b.getX(), p3b.getY());
        }
    }
}
