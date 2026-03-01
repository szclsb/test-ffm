package ch.szclsb.test.ffm.impl;

import ch.szclsb.test.ffm.api.ForeignObject;
import ch.szclsb.test.ffm.api.TargetType;
import ch.szclsb.test.ffm.api.pointer.ForeignPointer;
import ch.szclsb.test.ffm.api.ForeignFactory;
import ch.szclsb.test.ffm.api.structs.ForeignPoint;
import ch.szclsb.test.ffm.api.values.ForeignInt;
import ch.szclsb.test.ffm.api.vectors.ForeignVec4;
import ch.szclsb.test.ffm.impl.pointer.ForeignPointerImpl;
import ch.szclsb.test.ffm.impl.structs.ForeignPointImpl;
import ch.szclsb.test.ffm.impl.values.ForeignIntImpl;
import ch.szclsb.test.ffm.impl.vectors.ForeignVec4Impl;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ForeignFactoryImpl implements ForeignFactory {
    private record ForeignTypeInfo<T>(
            MemoryLayout memoryLayout,
            BiFunction<MemorySegment, Type, ? extends T> dereferenceFunction
    ) {
        ForeignTypeInfo(MemoryLayout targetLayout,
                        Function<MemorySegment, ? extends T> dereferenceFunction) {
            this(targetLayout, (segment, _) -> dereferenceFunction.apply(segment));
        }
    }

    private static final Map<Type, ForeignTypeInfo<?>> foreignTypeRegister = Collections.unmodifiableMap(registerTypes());

    private static Map<Type, ForeignTypeInfo<?>> registerTypes() {
        var map = new HashMap<Type, ForeignTypeInfo<?>>();
        map.put(ForeignPointer.class, new ForeignTypeInfo<ForeignPointer<?>>(ForeignPointer.LAYOUT, (segment, type) -> {
            var innerType = ((ParameterizedType) type).getActualTypeArguments()[0];  // access inner type R of ForeignPointer<R>
            return createPointer(segment, innerType);
        }));
        map.put(ForeignInt.class, new ForeignTypeInfo<ForeignInt>(ForeignInt.LAYOUT, ForeignIntImpl::new));
        map.put(ForeignPoint.class, new ForeignTypeInfo<ForeignPoint>(ForeignPoint.LAYOUT, ForeignPointImpl::new));
        map.put(ForeignVec4.class, new ForeignTypeInfo<ForeignVec4>(ForeignVec4.LAYOUT, ForeignVec4Impl::new));
        return map;
    }

    public static MemoryLayout getMemoryLayout(Class<? extends ForeignObject> foreignClass) {
        return Optional.ofNullable(foreignTypeRegister.get(foreignClass))
                .map(ForeignTypeInfo::memoryLayout)
                .orElseThrow(() -> new RuntimeException("foreign class %s is not registered".formatted(foreignClass.getCanonicalName())));
    }

    private final Arena session;

    public ForeignFactoryImpl(Arena session) {
        this.session = session;
    }

    private MemorySegment allocate(MemoryLayout layout) {
        return session.allocate(layout);
    }

    @Override
    public <T extends ForeignObject> ForeignPointer<T> allocatePointer(TargetType<T> targetType) {
        var segment = allocate(ForeignPointer.LAYOUT);
        return createPointer(segment, targetType.getType());
    }

    @SuppressWarnings("unchecked")
    private static <T extends ForeignObject> ForeignPointerImpl<T> createPointer(MemorySegment segment, Type targetType) {
        var targetOuterType = outer(targetType);
        var targetTypeInfo = (ForeignTypeInfo<T>) foreignTypeRegister.get(targetOuterType);
        return new ForeignPointerImpl<>(segment, targetTypeInfo.memoryLayout, refSegment ->
                targetTypeInfo.dereferenceFunction().apply(refSegment, targetType));  // use targetType - not targetOuterType, otherwise inner type info will be lost.
    }

    private static Type outer(Type type) {
        return type instanceof ParameterizedType pType
                ? pType.getRawType()
                : type;
    }

    @Override
    public ForeignInt allocateInt() {
        var segment = allocate(ForeignInt.LAYOUT);
        return new ForeignIntImpl(segment);
    }

    @Override
    public ForeignInt allocateInt(int value) {
        var foreignInt = allocateInt();
        foreignInt.setValue(value);
        return foreignInt;
    }

    @Override
    public ForeignPoint allocatePoint() {
        var segment = allocate(ForeignPoint.LAYOUT);
        return new ForeignPointImpl(segment);
    }

    @Override
    public ForeignPoint allocatePoint(int x, int y) {
        var foreignPoint = allocatePoint();
        foreignPoint.setX(x);
        foreignPoint.setY(y);
        return foreignPoint;
    }

    @Override
    public ForeignVec4 allocateVec4() {
        var segment = allocate(ForeignVec4.LAYOUT);
        return new ForeignVec4Impl(segment);
    }

    @Override
    public ForeignVec4 allocateVec4(float x, float y, float z, float w) {
        var vec4 = allocateVec4();
        vec4.setValues(x, y, z, w);
        return vec4;
    }
}
