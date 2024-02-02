package org.midheaven.lang.reflection;

import org.midheaven.collections.Sequence;

final class ParametricTypeReferenceClassWrapper<T> extends ParametricTypeReference<T> {

    private final Class<T> type;

    ParametricTypeReferenceClassWrapper(Class<T> type){
        this.type = type;
    }

    @Override
    public T cast(Object instance) {
        return type.cast(instance);
    }

    @Override
    public Class<?> rootClass() {
        return type;
    }

    @Override
    public Sequence<TypeReference> parameterTypes() {
        return Sequence.builder().empty();
    }

    @Override
    public boolean isAssignableTo(Class<?> other) {
        return other != null && other.isAssignableFrom(type);
    }

    @Override
    public boolean isInstance(Object instance) {
        return type.isInstance(instance);
    }

    @Override
    public boolean isPrimitive() {
        return type.isPrimitive();
    }
}
