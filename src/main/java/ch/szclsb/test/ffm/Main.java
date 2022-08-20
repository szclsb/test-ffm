package ch.szclsb.test.ffm;

import java.lang.foreign.*;

public class Main {
    private static final Linker LINKER = Linker.nativeLinker();
    private static final SymbolLookup LOADER = SymbolLookup.loaderLookup();

    public static void main(String[] args) throws Throwable {
        var dir = System.getProperty("user.dir");
        System.load(dir + "/lib/test_ffm_native.dll");
        var symbol = LOADER.lookup("printHello");
        var method = LINKER.downcallHandle(symbol.orElseThrow(),
                FunctionDescriptor.ofVoid());
        method.invoke();
    }
}
