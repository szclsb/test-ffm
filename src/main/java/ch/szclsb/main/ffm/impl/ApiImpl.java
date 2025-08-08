package ch.szclsb.main.ffm.impl;

import ch.szclsb.main.ffm.impl.structs.PointNativeImpl;
import ch.szclsb.main.ffm.impl.pointer.VoidPointer;
import ch.szclsb.main.ffm.export.Api;
import ch.szclsb.main.ffm.export.Pointer;
import ch.szclsb.main.ffm.export.Ref;
import ch.szclsb.main.ffm.export.structs.PointNative;
import ch.szclsb.main.ffm.export.values.IntNative;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public class ApiImpl implements Api {
    private static final Linker LINKER = Linker.nativeLinker();
    private static final SymbolLookup LOADER = SymbolLookup.loaderLookup();

    private static MemorySegment loadSymbol(String name) {
        return LOADER.find(name).orElseThrow(() -> new UnsatisfiedLinkError("unable to find symbol " + name));
    }

    private static void upcall(MemorySegment segment) {
        var message = segment.getString(0);
        System.out.println("Upcall: " + message);
    }

    private final Arena session;

    private final MethodHandle printHelloNative;
    private final MethodHandle passHelloNative;
    private final MethodHandle getHelloNative;
    private final MethodHandle allocateForeignIntNative;
    private final MethodHandle freeForeignNative;
    private final MethodHandle addFloatNative;
    private final MethodHandle vec4addNative;
    private final MethodHandle pointAddRefNative;
    private final MethodHandle pointAddNative;
    private final MethodHandle createInstanceNative;
    private final MethodHandle useInstanceNative;
    private final MethodHandle destroyInstanceNative;
    private final MethodHandle increment_int_native;
    private final MethodHandle increment_p_int_native;
    private final MethodHandle increment_pp_int_native;

    private final MemorySegment upcallStub;

    public ApiImpl(Arena session) {
        this.session = session;

        var dir = System.getProperty("user.dir");
        System.load(dir + "/build-native/Debug/ffm.dll");
        this.printHelloNative = LINKER.downcallHandle(loadSymbol("printHello"), FunctionDescriptor.ofVoid());
        this.passHelloNative = LINKER.downcallHandle(loadSymbol("passHello"), FunctionDescriptor.ofVoid(
                ValueLayout.ADDRESS));
        this.getHelloNative = LINKER.downcallHandle(loadSymbol("getHello"), FunctionDescriptor.of(ValueLayout.ADDRESS.withTargetLayout(MemoryLayout.sequenceLayout(16, ValueLayout.JAVA_CHAR))));
        this.allocateForeignIntNative = LINKER.downcallHandle(loadSymbol("allocateForeignInt"), FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.JAVA_INT));
        this.freeForeignNative = LINKER.downcallHandle(loadSymbol("freeForeign"), FunctionDescriptor.ofVoid(ValueLayout.ADDRESS));
        this.addFloatNative = LINKER.downcallHandle(loadSymbol("addFloat"), FunctionDescriptor.of(ValueLayout.JAVA_FLOAT,
                ValueLayout.JAVA_FLOAT,
                ValueLayout.JAVA_FLOAT));
        this.vec4addNative = LINKER.downcallHandle(loadSymbol("vec4add"), FunctionDescriptor.ofVoid(
                ValueLayout.ADDRESS,
                ValueLayout.ADDRESS,
                ValueLayout.ADDRESS));
        this.pointAddRefNative = LINKER.downcallHandle(loadSymbol("pointAddRef"), FunctionDescriptor.ofVoid(
                ValueLayout.ADDRESS,
                ValueLayout.ADDRESS,
                ValueLayout.ADDRESS));
        this.pointAddNative = LINKER.downcallHandle(loadSymbol("pointAdd"), FunctionDescriptor.of(PointNativeImpl.LAYOUT,
                PointNativeImpl.LAYOUT,
                PointNativeImpl.LAYOUT));
        this.createInstanceNative = LINKER.downcallHandle(loadSymbol("createInstance"), FunctionDescriptor.ofVoid(
                ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT,
                ValueLayout.ADDRESS));
        this.useInstanceNative = LINKER.downcallHandle(loadSymbol("useInstance"), FunctionDescriptor.ofVoid(
                ValueLayout.ADDRESS));
        this.destroyInstanceNative = LINKER.downcallHandle(loadSymbol("destroyInstance"), FunctionDescriptor.ofVoid(
                ValueLayout.ADDRESS));
        this.increment_int_native = LINKER.downcallHandle(loadSymbol("increment_int"), FunctionDescriptor.of(ValueLayout.JAVA_INT,
                ValueLayout.JAVA_INT
        ));
        this.increment_p_int_native = LINKER.downcallHandle(loadSymbol("increment_p_int"), FunctionDescriptor.ofVoid(
                ValueLayout.ADDRESS
        ));
        this.increment_pp_int_native = LINKER.downcallHandle(loadSymbol("increment_pp_int"), FunctionDescriptor.ofVoid(
                ValueLayout.ADDRESS
        ));

        try {
            var upcallDescriptor = FunctionDescriptor.ofVoid(ValueLayout.ADDRESS.withTargetLayout(MemoryLayout.sequenceLayout(256, ValueLayout.JAVA_CHAR)));
            var methodHandle = MethodHandles.lookup().findStatic(ApiImpl.class, "upcall", upcallDescriptor.toMethodType());
            this.upcallStub = LINKER.upcallStub(methodHandle, upcallDescriptor, session);
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
    public String getHello() throws Throwable {
        var rSegment = (MemorySegment) getHelloNative.invoke();
        return rSegment.getString(0);
    }

    @Override
    public MemorySegment allocateForeignInt(int value) throws Throwable {
        var rSegment = (MemorySegment) allocateForeignIntNative.invoke(value);
        return rSegment;
    }

    @Override
    public void freeForeign(MemorySegment pointer) throws Throwable {
        freeForeignNative.invoke(pointer);
    }

    @Override
    public float addFloat(float a, float b) throws Throwable {
        var r = (float) addFloatNative.invoke(a, b);
        return r;
    }

    @Override
    public void vec4add(Vector4 a, Vector4 b, Vector4 r) throws Throwable {
        vec4addNative.invoke(a.getSegment(), b.getSegment(), r.getSegment());
    }

    @Override
    public void pointAddRef(Ref<PointNative> a, Ref<PointNative> b, Ref<PointNative> r) throws Throwable {
        pointAddRefNative.invoke(a.getAddress(), b.getAddress(), r.getAddress());
    }

    @Override
    public PointNative pointAdd(PointNative a, PointNative b) throws Throwable {
        // note session is required as first arg
        var rSegment = (MemorySegment) pointAddNative.invoke(session, a.getSegment(), b.getSegment());
        return PointNativeImpl.ofSegment(rSegment);
    }

    @Override
    public Pointer createInstance(int a, int b) throws Throwable {
        var pSegment = session.allocate(ValueLayout.ADDRESS);
        createInstanceNative.invoke(a, b, pSegment);
        return new VoidPointer(pSegment.get(ValueLayout.ADDRESS, 0));
    }

    @Override
    public void useInstance(Pointer instance) throws Throwable {
        useInstanceNative.invoke(instance.getAddress());
    }

    @Override
    public void destroyInstance(Pointer instance) throws Throwable {
        destroyInstanceNative.invoke(instance.getAddress());
    }

    @Override
    public int incrementInt(int value) throws Throwable {
        return (int) increment_int_native.invoke(value);
    }

    @Override
    public void incrementPInt(Ref<IntNative> pValue) throws Throwable {
        increment_p_int_native.invoke(pValue.getAddress());
    }

    @Override
    public void incrementPpInt(Ref<Ref<IntNative>> ppValue) throws Throwable {
        increment_pp_int_native.invoke(ppValue.getAddress());
    }
}
