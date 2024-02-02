package org.midheaven.lang.reflection;

public abstract class ParametricTypeReference<T> extends TypeReference {

    public abstract T cast(Object instance);

}
