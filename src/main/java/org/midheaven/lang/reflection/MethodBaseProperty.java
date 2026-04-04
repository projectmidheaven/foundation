package org.midheaven.lang.reflection;

import org.midheaven.lang.Maybe;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Represents Method Base Property.
 */
final class MethodBaseProperty implements Property {

    private final PropertyMetaInfo info;
    private final Method getter;
    private final Method setter;
    private final Class<?> valueType;
    private final Class<?> readType;
    private final Field field;

    MethodBaseProperty(PropertyMetaInfo info,  Class<?> valueType, Class<?> readType) {
        this.info =  info;
        this.getter = info.accessor;
        this.setter =  info.modifier;
        this.field =   info.field;
        this.valueType = valueType;
        this.readType = readType;
    }

    @Override
    public String name() {
        return info.propertyName;
    }

    @Override
    public Class<?> valueType() {
        return valueType;
    }

    @Override
    public boolean isOptional() {
        return Optional.class.isAssignableFrom(readType)
            || Maybe.class.isAssignableFrom(readType);
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
    public Maybe<Object> getValue(Object instance) {
        if (getter != null){
            try {
                return Maybe.of(getter.invoke(instance));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new ReflectionException(e);
            }
        } else if (field != null){
            try {
                return Maybe.of(field.get(instance));
            } catch (IllegalAccessException e) {
                throw new ReflectionException(e);
            }
        }

        return Maybe.none();
    }

    @Override
    public void setValue(Object instance, Object value) {
        try {
            if (setter != null) {
                try {
                    setter.invoke(instance, value);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new ReflectionException(e);
                }
            } else if (field != null) {
                try {
                    field.set(instance, value);
                } catch (IllegalAccessException e) {
                    throw new ReflectionException(e);
                }
            }
        } catch (IllegalArgumentException e){
            throw new ReflectionException(
                    "Cannot set value "
                    + value
                    + " of type " + value.getClass()
                    + " into property "
                    + this.info.declaringClass.getName()
                    + "." + this.info.propertyName
                    + " of type " + this.valueType
            );
        }

    }
}
