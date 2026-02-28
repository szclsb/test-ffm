package ch.szclsb.test.ffm.api;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TargetType<T extends ForeignObject> {
    private final Type type;

    protected TargetType() {
        var superType = (ParameterizedType) getClass().getGenericSuperclass();
        this.type = superType.getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }
}
