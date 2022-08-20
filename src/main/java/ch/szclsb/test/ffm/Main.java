package ch.szclsb.test.ffm;

public class Main {
    public static void main(String[] args) throws Throwable {
        var nativeMethodHandler = new NativeMethodHandlerImpl();
        nativeMethodHandler.printHello();
    }
}
