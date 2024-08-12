package org.midheaven.lang.reflection;

import org.midheaven.lang.Maybe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

class PropertyMirrorSupport {

    static Maybe<Property>  fromAccessor(Method accessor, Class<?> declaringClass) {
        var propertyName = propertyNameFromAccessor(accessor);

        var info = new PropertyMetaInfo();
        info.declaringClass = declaringClass;
        info.accessor = accessor;
        info.propertyName = propertyName;

        return from(extractRemaining(info));
    }

    static String propertyNameFromAccessor(Method accessor){
        var propertyName = accessor.getName();
        var prefixLength = propertyName.startsWith("is")
                ? 2
                : propertyName.startsWith("get") ? 3 : 0;

        propertyName = propertyName.substring(prefixLength);
        return propertyName.substring(0,1).toUpperCase() + propertyName.substring(1);
    }


    static Maybe<Property> fromModifier(Method modifier, Class<?> declaringClass) {
        var name = modifier.getName();
        if (!name.startsWith("set")){
            return Maybe.none();
        }
        name = name.substring(3);
        var propertyName = name.substring(0,1).toUpperCase() + name.substring(1);

        var info = new PropertyMetaInfo();
        info.declaringClass = declaringClass;
        info.modifier = modifier;
        info.propertyName = propertyName;

        return from(extractRemaining(info));
    }

    static Maybe<Property>  fromField(Field field, Class<?> declaringClass) {
        var name = field.getName();
        var propertyName = name.substring(0,1).toUpperCase() + name.substring(1);

        var info = new PropertyMetaInfo();
        info.declaringClass = declaringClass;
        info.field = field;
        info.propertyName = propertyName;

        return from(extractRemaining(info));
    }


    private static PropertyMetaInfo extractRemaining(PropertyMetaInfo info){
        if (info.accessor == null){
            extractAccessor(info);
        }

        if ( info.modifier == null){
            extractModified(info);
        }

        if (info.field == null){
            extractField(info);
        }

        return info;
    }


    private static void extractAccessor(PropertyMetaInfo info) {
        for (var prefix : List.of("get", "is", "")){
            try {
                info.accessor = info.declaringClass.getDeclaredMethod(prefix + info.propertyName);
            } catch (NoSuchMethodException e) {
                // try next prefix
            }
        }
    }

    private static void extractModified(PropertyMetaInfo info) {
        var declaringClass = info.declaringClass;
        var propertyName = info.propertyName;
        if (info.valueType().isPresent()){
            try {
                info.modifier = declaringClass.getDeclaredMethod("set" + propertyName, info.valueType().get());
            } catch (NoSuchMethodException e) {
                // no modified
            }
        } else {
            Stream.of(declaringClass.getDeclaredMethods())
                    .filter(m -> m.getParameterCount() == 1
                            && m.getName().equalsIgnoreCase("set" + propertyName)
                    )
                    .findFirst()
                    .ifPresent(method -> info.modifier = method);
        }
    }

    private static void extractField(PropertyMetaInfo info) {
        var currentClass = info.declaringClass;

        while(currentClass != null){
            var stream = Arrays.stream(currentClass.getDeclaredFields())
                    .filter(f -> f.getName().equalsIgnoreCase(info.propertyName));
            if (info.valueType().isPresent()){
                stream = stream.filter(f -> f.getType().isAssignableFrom(info.valueType().get()));
            }
            var possibleField = stream.findFirst();
            if (possibleField.isPresent()){
                info.field = possibleField.get();
                return;
            }
            currentClass = currentClass.getSuperclass();
        }
    }

    private static Maybe<Property> from(PropertyMetaInfo info) {
        return Maybe.<Property>of(new MethodBaseProperty(
                info.propertyName,
                info.accessor,
                info.modifier,
                info.field,
                info.valueType().orElse(null),
                info.accessor != null ? info.accessor.getReturnType() : null
        )).filter(p -> info.accessor != null || info.modifier != null); // single fields are not properties
    }

    private PropertyMirrorSupport(){}
}

class PropertyMetaInfo {
    public Class<?> declaringClass;
    public String propertyName;
    public Method accessor;
    public Method modifier;
    public Field field;

    public Optional<Class<?>> valueType() {
        if (field != null){
            return Optional.of(field.getType());
        } else if (modifier != null){
            return Optional.of(modifier.getParameterTypes()[0]);
        }
        return Optional.empty();
    }
}