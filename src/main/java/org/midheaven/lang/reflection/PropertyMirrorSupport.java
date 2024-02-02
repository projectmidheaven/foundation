package org.midheaven.lang.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

class PropertyMirrorSupport {


    static String propertyNameFromAccessor(Method accessor){
        var propertyName = accessor.getName();
        var prefixLength = propertyName.startsWith("is")
                ? 2
                : propertyName.startsWith("get") ? 3 : 0;

        propertyName = propertyName.substring(prefixLength);
        return propertyName.substring(0,1).toUpperCase() + propertyName.substring(1);
    }

    static Optional<Property>  fromAccessor(Method accessor, Class<?> declaringClass) {
        var propertyName = propertyNameFromAccessor(accessor);
        try {
            var modifier = declaringClass.getDeclaredMethod("set" + propertyName, accessor.getReturnType());
            return from(declaringClass, propertyName, accessor, modifier);
        } catch (NoSuchMethodException e) {
            return from(declaringClass, propertyName, accessor, null);
        }
    }

    static Optional<Property> fromModifier(Method modifier, Class<?> declaringClass) {
        var name = modifier.getName();
        if (!name.startsWith("set")){
            return Optional.empty();
        }
        name = name.substring(3);
        var propertyName = name.substring(0,1).toUpperCase() + name.substring(1);

        return tryFindAccessor(declaringClass, propertyName,  modifier.getReturnType())
                .flatMap(accessor -> from(declaringClass, propertyName, accessor, modifier))
                .or(() -> from(declaringClass, propertyName, null, modifier));
    }

    static Optional<Method> tryFindAccessor( Class<?> declaringClass, String propertyName, Class<?> type){
        for (var prefix : List.of("get", "is", "")){
            try {
                return Optional.of(declaringClass.getDeclaredMethod(prefix + propertyName ,type));
            } catch (NoSuchMethodException e) {
                // try next prefix
            }
        }
        return Optional.empty();
    }

    static Optional<Property>  fromField(Field field, Class<?> declaringClass) {
        var name = field.getName();
        var propertyName = name.substring(0,1).toUpperCase() + name.substring(1);

        try {
            var modifier = declaringClass.getDeclaredMethod("set" + propertyName, field.getType());
            return fromModifier(modifier, declaringClass);
        } catch (NoSuchMethodException e) {
            return tryFindAccessor(declaringClass, propertyName, field.getType())
                    .flatMap(accessor -> from(declaringClass, propertyName, accessor, null));
        }
    }

    private static Optional<Property>  from(Class<?> declaringClass,String propertyName, Method accessor, Method modifier) {
        return Optional.of(new MethodBaseProperty(propertyName, accessor, modifier));
    }

    private PropertyMirrorSupport(){}
}
