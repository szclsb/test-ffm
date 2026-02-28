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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class ForeignFactoryImpl implements ForeignFactory {
    private record ForeignDefinition<T>(
            MemoryLayout targetLayout,
            BiFunction<MemorySegment, Type, ? extends T> dereferenceFunction
    ) {
    }

    private static Map<Class<?>, ForeignDefinition<?>> dereferenceFunctions = registerTypes();
    private static Map<Class<?>, ForeignDefinition<?>> registerTypes() {
        var map = new HashMap<Class<?>, ForeignDefinition<?>>();
        map.put(ForeignPointer.class, new ForeignDefinition<ForeignPointer<?>>(ForeignPointer.LAYOUT, (segment, type) -> {
            var innerType = ((ParameterizedType) type).getActualTypeArguments()[0];  // access inner type of ForeignPointer
            return allocatePointer(segment, innerType);
        }));
        map.put(ForeignInt.class, new ForeignDefinition<ForeignInt>(ForeignInt.LAYOUT, (segment, type) -> new ForeignIntImpl(segment)));
        map.put(ForeignPoint.class, new ForeignDefinition<ForeignPoint>(ForeignPoint.LAYOUT, (segment, type) -> new ForeignPointImpl(segment)));
        map.put(ForeignVec4.class, new ForeignDefinition<ForeignVec4>(ForeignVec4.LAYOUT, (segment, type) -> new ForeignVec4Impl(segment)));
        return map;
    }

    private static Type getOuterType(Type type) {
        if (type instanceof ParameterizedType pType) {
            return pType.getRawType();
        }

        return type;
    }

    public static MemoryLayout getLayout(Class<? extends ForeignObject> foreignClass) {
        return Optional.ofNullable(dereferenceFunctions.get(foreignClass))
                .map(ForeignDefinition::targetLayout)
                .orElse(null);
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
        return allocatePointer(segment, targetType.getType());
    }

    private static <T extends ForeignObject> ForeignPointerImpl<T> allocatePointer(MemorySegment segment, Type targetType) {
        var rawType = getOuterType(targetType);  // access raw type to retrieve dereference function
        var dereferenceFunction = (ForeignDefinition<T>) dereferenceFunctions.get(rawType);
        return new ForeignPointerImpl<>(segment, dereferenceFunction.targetLayout, refSegment ->
                dereferenceFunction.dereferenceFunction().apply(refSegment, targetType));
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
