package org.midheaven.lang.reflection;

import org.midheaven.lang.Maybe;

public interface Property {

    String name();
    Class<?> valueType();
    boolean isOptional();

    boolean canRead();
    boolean canWrite();

    Maybe<Object> getValue(Object instance);
    void setValue(Object instance, Object value);
}
