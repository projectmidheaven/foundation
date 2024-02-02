package org.midheaven.lang.reflection;

import org.midheaven.collections.Sequence;

import java.lang.reflect.Type;

public abstract class TypeReference {

    public static <X> ParametricTypeReference<X> of(Class<X> type){
        return new ParametricTypeReferenceClassWrapper<>(type);
    }

    public static TypeReference of(Type type){
        return new TypeReferenceTypeAdapter(type);
    }

    public abstract Class<?> rootClass();
    public abstract Sequence<TypeReference> parameterTypes();

    public abstract boolean isAssignableTo(Class<?> other);
    public abstract boolean isInstance(Object instance);
    public abstract boolean isPrimitive();

    @Override
    public final boolean equals(Object other) {
        return other instanceof TypeReference reference
                && this.rootClass().equals(reference.rootClass())
                && this.parameterTypes().equals(reference.parameterTypes());
    }

    @Override
    public final int hashCode() {
        return this.rootClass().hashCode();
    }
}
