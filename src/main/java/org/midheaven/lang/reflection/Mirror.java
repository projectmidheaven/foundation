package org.midheaven.lang.reflection;


import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Optional;

public class Mirror<T> {

    private static final ProxyEngine engine = new CompositeProxyEngine()
            .add(new NativeMirrorEngine())
            .add(new ByteBuddyProxyEngine())
            ;

    public static <X> Mirror<X> reflect(Class<X> type){
        return new Mirror<>(type);
    }

    public static Optional<Property> reflect(Method method){
        if (method.getName().startsWith("set") && method.getParameterTypes().length == 1){
            return PropertyMirrorSupport.fromModifier(method, method.getDeclaringClass());
        } else if (method.getParameterTypes().length == 0){
            return PropertyMirrorSupport.fromAccessor(method, method.getDeclaringClass());
        }
        return Optional.empty();
    }

    public static Optional<Property> reflect(Field field){
        return PropertyMirrorSupport.fromField(field, field.getDeclaringClass());
    }

    private final Class<T> type;
    private PropertiesMirror<T> properties;

    Mirror(Class<T> type) {
        this.type = type;
    }

    public Optional<ParameterizedType> parameterizedType(){
        return extractParameterizedType(this.type);
    }

    private Optional<ParameterizedType> extractParameterizedType(Class<?> type){
        if (type.getGenericSuperclass() instanceof ParameterizedType parameterizedType){
            return Optional.of(parameterizedType);
        } else if (type.getSuperclass() == null || type.getSuperclass().equals(Object.class)){
            return Optional.empty();
        }
        return extractParameterizedType(type.getSuperclass());
    }

    public PropertiesMirror<T> properties(){
        if (properties ==null){
            properties = new ReflectionPropertiesMirror<>(this.type);
        }
        return properties;
    }
    public T newInstance(){
        Constructor<T> constructor;
        try {
            constructor = type.getConstructor();
        } catch (NoSuchMethodException e) {
            try {
                constructor = type.getDeclaredConstructor();
            } catch (NoSuchMethodException ex) {
                throw new ReflectionException(ex);
            }
        }

        try {
            constructor.setAccessible(true);
            return constructor.newInstance();
        }catch ( InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new ReflectionException(e);
        }
    }

    public T newInstance(Object ... parameters){
        var constructor =  Arrays.stream(type.getDeclaredConstructors())
                .filter(it -> it.getParameterTypes().length == parameters.length)
                .filter(it -> {
                    int index = 0;
                    for (var type : it.getParameterTypes()){
                        var value = parameters[index++];
                        if (value != null && !canAssign(value.getClass(), type)){
                            return false;
                        }
                    }
                    return true;
                }).findFirst().orElseThrow(() -> new ReflectionException("No suitable constructor found for parameters " + Arrays.toString(parameters)));
        try {
            constructor.setAccessible(true);
            return type.cast(constructor.newInstance(parameters));
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new ReflectionException(e);
        }
    }

    private static boolean canAssign(Class<?> source, Class<?> target){
        if (target.isAssignableFrom(source)){
            return true;
        } else if (target.isPrimitive()){
            Class<?> boxed=Array.get(Array.newInstance(target,1),0).getClass();
            return boxed.isAssignableFrom(source);
        }
        return false;
    }

    public T proxy(InvocationHandler handler, Class<?>...  otherTypes){
        if (engine.canProxy(this.type)){
            return engine.proxy(this.type, otherTypes, handler);
        }

        throw new ReflectionException("Is not possible to proxy " + this.type.getName());
    }
}
