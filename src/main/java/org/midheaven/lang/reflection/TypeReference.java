
package org.midheaven.lang.reflection;

import org.midheaven.collections.Sequence;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public abstract sealed class TypeReference permits ParametricTypeReference, TypeReferenceTypeAdapter{

    public static <X> ParametricTypeReference<X> of(Class<X> type){
        return new SequenceParametricTypeReference<>(type);
    }

    public static <K,V, M extends Map<K,V>> ParametricTypeReference<M> map(Class<? extends M> mapType, Class<K> keyType, Class<V> valueType){
        return new SequenceParametricTypeReference(mapType, Sequence.builder().of(TypeReference.of(keyType), TypeReference.of(valueType)));
    }


    public static <T,C extends Collection<T>> ParametricTypeReference<C> collection(Class<? extends C> collectionType, Class<T> elementType){
        return new SequenceParametricTypeReference(collectionType, Sequence.builder().of(TypeReference.of(elementType)));
    }

    public static TypeReference of(Type type){
        return new TypeReferenceTypeAdapter(type);
    }

    public final TypeKind kind(){
        var root = this.rootClass();
        if (root.isPrimitive()){
            return TypeKind.PRIMITIVE;
        } if (root.isInterface()){
            return TypeKind.INTERFACE;
        }  else if (root.isEnum()){
            return TypeKind.ENUM;
        } else if (root.isRecord()){
            return TypeKind.RECORD;
        } else if (root.isArray()){
            return TypeKind.ARRAY;
        } else if (root.isAnnotation()){
            return TypeKind.ANNOTATION;
        }
        return TypeKind.CLASS;
    }

    public abstract boolean isPrimitive();
    public abstract Class<?> rootClass();
    public abstract Sequence<TypeReference> parameterTypes();

    public abstract boolean isAssignableTo(Class<?> other);
    public boolean isAssignableTo(TypeReference other){
        return isAssignableTo(other.rootClass());
    }

    public abstract boolean isInstance(Object instance);


    @Override
    public abstract String toString();

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
