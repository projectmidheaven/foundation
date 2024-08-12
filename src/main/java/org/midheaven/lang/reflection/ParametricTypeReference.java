package org.midheaven.lang.reflection;

import org.midheaven.collections.Sequence;

public abstract sealed class ParametricTypeReference<T>
        extends TypeReference
        permits ParametricTypeReferenceAdapter, ReifiedTypeReference, SequenceParametricTypeReference {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <X> ParametricTypeReference<X> from(TypeReference reference){
        if (reference instanceof ParametricTypeReference<?> parametricTypeReference){
            return (ParametricTypeReference<X>) parametricTypeReference;
        } else if (reference instanceof TypeReferenceTypeAdapter adapter && adapter.type instanceof Class<?> type){
            return new SequenceParametricTypeReference(type, adapter.parameterTypes);
        }
        return new ParametricTypeReferenceAdapter<>(reference);
    }

    public abstract T cast(Object instance);

    @Override
    public abstract Class<T> rootClass();
}


final class ParametricTypeReferenceAdapter<T> extends ParametricTypeReference<T> {

    TypeReference typeReference;
    ParametricTypeReferenceAdapter(TypeReference typeReference){
        this.typeReference = typeReference;
    }

    @Override
    public T cast(Object instance) {
        return rootClass().cast(instance);
    }

    @Override
    public boolean isPrimitive() {
        return typeReference.isPrimitive();
    }

    @Override
    public Class<T> rootClass() {
        return (Class<T>) typeReference.rootClass();
    }

    @Override
    public Sequence<TypeReference> parameterTypes() {
        return typeReference.parameterTypes();
    }

    @Override
    public boolean isAssignableTo(Class<?> other) {
        return typeReference.isAssignableTo(other);
    }

    @Override
    public boolean isInstance(Object instance) {
        return typeReference.isInstance(instance);
    }

    @Override
    public String toString() {
        return typeReference.toString();
    }
}