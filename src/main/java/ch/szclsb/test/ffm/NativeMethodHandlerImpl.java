package ch.szclsb.test.ffm;

import ch.szclsb.test.ffm.export.INativeMethodHandler;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class NativeMethodHandlerImpl implements INativeMethodHandler {
    private static final Linker LINKER = Linker.nativeLinker();
    private static final SymbolLookup LOADER = SymbolLookup.loaderLookup();

    private static MemorySegment loadSymbol(String name) {
        return LOADER.lookup(name).orElseThrow(() -> new UnsatisfiedLinkError("unable to find symbol " + name));
    }

    private final MethodHandle printHelloNative;
    private final MethodHandle vec4addNative;

    public NativeMethodHandlerImpl() {
        var dir = System.getProperty("user.dir");
        System.load(dir + "/lib/test_ffm_native.dll");
        this.printHelloNative = LINKER.downcallHandle(loadSymbol("printHello"), FunctionDescriptor.ofVoid());
        this.vec4addNative = LINKER.downcallHandle(loadSymbol("vec4add"), FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS));
    }

    @Override
    public void printHello() throws Throwable {
        printHelloNative.invoke();
    }

    @Override
    public void vec4add(Vector4 a, Vector4 b, Vector4 r) throws Throwable {
        vec4addNative.invoke(a.getAddress(), b.getAddress(), r.getAddress());
    }
}
