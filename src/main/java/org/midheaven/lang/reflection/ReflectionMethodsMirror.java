package org.midheaven.lang.reflection;

import org.midheaven.collections.Array;

public class ReflectionMethodsMirror<T> implements MethodsMirror<T> {

    private final Class<T> type;

    public ReflectionMethodsMirror(Class<T> type) {
        this.type = type;
    }

    @Override
    public MethodMirrorQuery declared() {
        return new MethodMirrorQuery(Array.of(type.getDeclaredMethods()));
    }

    @Override
    public MethodMirrorQuery contracted() {
        return new MethodMirrorQuery(Array.of(type.getMethods()));
    }
}
