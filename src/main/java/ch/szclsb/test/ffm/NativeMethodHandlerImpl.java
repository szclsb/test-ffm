package ch.szclsb.test.ffm;

import ch.szclsb.test.ffm.export.INativeMethodHandler;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class NativeMethodHandlerImpl implements INativeMethodHandler {
    private static final Linker LINKER = Linker.nativeLinker();
    private static final SymbolLookup LOADER = SymbolLookup.loaderLookup();

    private static MemorySegment loadSymbol(String name) {
        return LOADER.lookup(name).orElseThrow(() -> new UnsatisfiedLinkError("unable to find symbol " + name));
    }

    private static void upcall(MemoryAddress address) {
        var message = address.getUtf8String(0);
        System.out.println("Upcall: " + message);
    }

    private final MemorySession session;

    private final MethodHandle printHelloNative;
    private final MethodHandle passHelloNative;
    private final MethodHandle vec4addNative;

    private final MemorySegment upcallStub;

    public NativeMethodHandlerImpl(MemorySession session) {
        this.session = session;

        var dir = System.getProperty("user.dir");
        System.load(dir + "/lib/test_ffm_native.dll");
        this.printHelloNative = LINKER.downcallHandle(loadSymbol("printHello"), FunctionDescriptor.ofVoid());
        this.passHelloNative = LINKER.downcallHandle(loadSymbol("passHello"), FunctionDescriptor.ofVoid(ValueLayout.ADDRESS));
        this.vec4addNative = LINKER.downcallHandle(loadSymbol("vec4add"), FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS));

        try {
            var methodHandle = MethodHandles.lookup().findStatic(NativeMethodHandlerImpl.class, "upcall", MethodType.methodType(void.class, MemoryAddress.class));
            this.upcallStub = LINKER.upcallStub(methodHandle, FunctionDescriptor.ofVoid(ValueLayout.ADDRESS), session);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printHello() throws Throwable {
        printHelloNative.invoke();
    }

    @Override
    public void passHello() throws Throwable {
        passHelloNative.invoke(upcallStub);
    }

    @Override
    public void vec4add(Vector4 a, Vector4 b, Vector4 r) throws Throwable {
        vec4addNative.invoke(a.getAddress(), b.getAddress(), r.getAddress());
    }
}
