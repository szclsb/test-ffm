package ch.szclsb.main.ffm;

import java.lang.foreign.Arena;

public class Main {
    public static void main(String[] args) throws Throwable {

        try(var session = Arena.openShared()) {
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
        }
    }
}
