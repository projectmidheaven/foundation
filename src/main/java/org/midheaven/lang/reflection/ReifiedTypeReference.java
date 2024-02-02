package org.midheaven.lang.reflection;

import org.midheaven.collections.Sequence;

import java.lang.reflect.ParameterizedType;

public abstract class ReifiedTypeReference<T> extends ParametricTypeReference<T> implements Comparable<ReifiedTypeReference<T>> {

    private final TypeReference typeReference;

    protected ReifiedTypeReference()
    {
        var reifiedType = ((ParameterizedType)  getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        typeReference = TypeReference.of(reifiedType);
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
    public boolean isPrimitive() {
        return typeReference.isPrimitive();
    }

    @Override
    public T cast(Object instance) {
        return (T)this.rootClass().cast(instance);
    }

    @Override
    public Class<?> rootClass() {
        return typeReference.rootClass();
    }

    @Override
    public Sequence<TypeReference> parameterTypes() {
       return typeReference.parameterTypes();
    }

    @Override
    public int compareTo(ReifiedTypeReference<T> other) {
        return equals(other) ? 0 : -1;
    }

    public String toString(){
        return typeReference.toString();
    }
}
