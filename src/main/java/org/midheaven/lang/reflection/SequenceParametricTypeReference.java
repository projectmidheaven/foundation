package org.midheaven.lang.reflection;

import org.midheaven.collections.Sequence;

final class SequenceParametricTypeReference<T> extends ParametricTypeReference<T> {

    private final Class<T> type;
    private final Sequence<TypeReference> parameterTypes;

    SequenceParametricTypeReference(Class<T> type, Sequence<TypeReference> parameterTypes) {
        this.type = type;
        this.parameterTypes = parameterTypes;
    }

    SequenceParametricTypeReference(Class<T> type) {
        this.type = type;
        this.parameterTypes = Sequence.builder().empty();
    }

    public String toString(){
        return type.toString();
    }


    @Override
    public T cast(Object instance) {
        return type.cast(instance);
    }

    @Override
    public boolean isPrimitive() {
        return type.isPrimitive();
    }

    @Override
    public Class<T> rootClass() {
       return type;
    }

    @Override
    public Sequence<TypeReference> parameterTypes() {
        return parameterTypes;
    }

    @Override
    public boolean isAssignableTo(Class<?> other) {
        return other.isAssignableFrom(this.rootClass());
    }

    @Override
    public boolean isInstance(Object instance) {
        return rootClass().isInstance(instance);
    }
}
