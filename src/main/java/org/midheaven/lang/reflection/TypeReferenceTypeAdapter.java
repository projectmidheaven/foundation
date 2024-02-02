package org.midheaven.lang.reflection;

import org.midheaven.collections.Sequence;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

final class TypeReferenceTypeAdapter extends TypeReference {

    private final Type type;
    private final Sequence<TypeReference> parameterTypes;

    TypeReferenceTypeAdapter(Type type) {
        this.type = type;
        if (type instanceof ParameterizedType parameterizedType){
            parameterTypes = Sequence.builder().of(parameterizedType.getActualTypeArguments()).map(it -> TypeReference.of(it));
        } else {
            parameterTypes = Sequence.builder().empty();
        }
    }
    public String toString(){
        return type.toString();
    }

    @Override
    public Class<?> rootClass() {
        if (type instanceof Class<?> c){
            return c;
        } else if (type instanceof ParameterizedType parameterizedType){
            return (Class<?>) parameterizedType.getRawType();
        }
        throw new UnsupportedOperationException("Unrecognized type " + type.getClass());
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

    @Override
    public boolean isPrimitive() {
        return type instanceof Class<?> c && c.isPrimitive();
    }
}
