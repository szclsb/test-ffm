package ch.szclsb.test.ffm;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.invoke.MethodHandle;

public class NativeMethodHandlerImpl implements INativeMethodHandler {
    private static final Linker LINKER = Linker.nativeLinker();
    private static final SymbolLookup LOADER = SymbolLookup.loaderLookup();

    private static MemorySegment loadSymbol(String name) {
        return LOADER.lookup(name).orElseThrow(() -> new UnsatisfiedLinkError("unable to find symbol " + name));
    }

    private final MethodHandle printHelloNative;

    public NativeMethodHandlerImpl() {
        var dir = System.getProperty("user.dir");
        System.load(dir + "/lib/test_ffm_native.dll");
        this.printHelloNative = LINKER.downcallHandle(loadSymbol("printHello"), FunctionDescriptor.ofVoid());
    }

    @Override
    public void printHello() throws Throwable {
        printHelloNative.invoke();
    }
}
