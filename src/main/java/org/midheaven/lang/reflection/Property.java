package org.midheaven.lang.reflection;

import java.util.Optional;

public interface Property {

    String name();
    Class<?> valueType();

    boolean canRead();
    boolean canWrite();

    Optional<Object> getValue(Object instance);
    void setValue(Object instance, Object value);
}
