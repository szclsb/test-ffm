import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.SymbolLookup;

import java.lang.invoke.MethodType;

public class Main {
    private static final CLinker CLINKER = CLinker.getInstance();
//    private static final SymbolLookup LOADER = SymbolLookup.loaderLookup();
    private static final SymbolLookup SYSTEM = CLinker.systemLookup();

    public static void main(String[] args) throws Throwable {
        var method = CLINKER.downcallHandle(SYSTEM.lookup("_getpid").orElseThrow(),
                MethodType.methodType(int.class),
                FunctionDescriptor.of(CLinker.C_INT));
        var pid = (int) method.invoke();

        System.out.println("java pid: " + ProcessHandle.current().pid());
        System.out.println("ffm  pid: " + pid);
    }
}
