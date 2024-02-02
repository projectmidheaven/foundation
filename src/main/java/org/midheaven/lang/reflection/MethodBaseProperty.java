package org.midheaven.lang.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

final class MethodBaseProperty implements Property {

    private final String name;
    private final Method getter;
    private final Method setter;

    MethodBaseProperty(String name, Method getter, Method setter) {
        this.name = name;
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Class<?> valueType() {
        return getter.getReturnType();
    }

    @Override
    public boolean canRead() {
        return getter != null;
    }

    @Override
    public boolean canWrite() {
        return setter != null;
    }

    @Override
    public Optional<Object> getValue(Object instance) {
        if (getter == null){
             return Optional.empty();
        }
        try {
            return Optional.ofNullable(getter.invoke(instance));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ReflectionException(e);
        }
    }

    @Override
    public void setValue(Object instance, Object value) {
        if (setter == null){
            return;
        }
        try {
            setter.invoke(instance, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ReflectionException(e);
        }
    }
}
