package org.midheaven.lang.reflection;


import org.midheaven.lang.Maybe;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.time.temporal.Temporal;
import java.util.Arrays;

/**
 * Represents Mirror.
 * @param <T> type being mirrored
 */
public class Mirror<T> {

    private static final ProxyEngine engine = new CompositeProxyEngine()
            .add(new ByteBuddyProxyEngine())
            .add(new NativeMirrorEngine())
            ;

    /**
     * Performs reflect.
     * @param type the type value
     * @return the result of reflect
     */
    public static <X> Mirror<X> reflect(Class<X> type){
        return new Mirror<>(type);
    }

    /**
     * Performs reflect.
     * @param type the type value
     * @return the result of reflect
     */
    public static <X> Mirror<X> reflect(ParametricTypeReference<X> type){
        return new Mirror<>(type.rootClass());
    }

    /**
     * Performs reflect.
     * @param method the method value
     * @return the result of reflect
     */
    public static Maybe<Property> reflect(Method method){
        if (method.getName().startsWith("set") && method.getParameterTypes().length == 1){
            return PropertyMirrorSupport.fromModifier(method, method.getDeclaringClass());
        } else if (method.getParameterTypes().length == 0){
            return PropertyMirrorSupport.fromAccessor(method, method.getDeclaringClass());
        }
        return Maybe.none();
    }

    /**
     * Performs reflect.
     * @param field the field value
     * @return the result of reflect
     */
    public static Maybe<Property> reflect(Field field){
        return PropertyMirrorSupport.fromField(field, field.getDeclaringClass());
    }

    /**
     * Checks whether is Possibly ABean.
     * @param type the type value
     * @return the result of isPossiblyABean
     */
    public static boolean isPossiblyABean(Class<?> type){
        return isPossiblyABean(ParametricTypeReference.of(type));
    }

    /**
     * Checks whether is Possibly ABean.
     * @param type the type value
     * @return the result of isPossiblyABean
     */
    public static boolean isPossiblyABean(ParametricTypeReference<?> type){
        return type.kind().isClass()
                && !type.isAssignableTo(String.class)
                && !type.isAssignableTo(Number.class)
                && !type.isAssignableTo(Iterable.class)
                && !type.isAssignableTo(Temporal.class)
                ;
    }

    private final Class<T> type;
    private PropertiesMirror<T> properties;
    private MethodsMirror<T> methods;
    private ConstructorsMirror<T> constructors;

    Mirror(Class<T> type) {
        this.type = type;
    }

    /**
     * Performs parameterizedType.
     * @return the result of parameterizedType
     */
    public Maybe<ParameterizedType> parameterizedType(){
        return extractParameterizedType(this.type);
    }

    /**
     * Performs extractParameterizedType.
     * @param type the type value
     * @return the result of extractParameterizedType
     */
    private Maybe<ParameterizedType> extractParameterizedType(Class<?> type){
        if (type.getGenericSuperclass() instanceof ParameterizedType parameterizedType){
            return Maybe.some(parameterizedType);
        } else if (type.getSuperclass() == null || type.getSuperclass().equals(Object.class)){
            return Maybe.none();
        }
        return extractParameterizedType(type.getSuperclass());
    }

    /**
     * Performs properties.
     * @return the result of properties
     */
    public PropertiesMirror<T> properties(){
        if (properties == null){
            properties = new ReflectionPropertiesMirror<>(this.type);
        }
        return properties;
    }

    /**
     * Performs methods.
     * @return the result of methods
     */
    public MethodsMirror<T> methods(){
        if (methods == null){
            methods = new ReflectionMethodsMirror<T>(this.type);
        }
        return methods;
    }

    /**
     * Performs constructors.
     * @return the result of constructors
     */
    public ConstructorsMirror<T> constructors(){
        if (constructors == null){
            constructors = new ReflectionConstructorsMirror<T>(this.type);
        }
        return constructors;
    }

    /**
     * Creates new Instance.
     * @return the result of newInstance
     */
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

    /**
     * Creates new Instance.
     * @param parameters the parameters value
     * @return the result of newInstance
     */
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

    /**
     * Checks whether can Assign.
     * @param source the source value
     * @param target the target value
     * @return the result of canAssign
     */
    private static boolean canAssign(Class<?> source, Class<?> target){
        if (target.isAssignableFrom(source)){
            return true;
        } else if (target.isPrimitive()){
            Class<?> boxed=Array.get(Array.newInstance(target,1),0).getClass();
            return boxed.isAssignableFrom(source);
        }
        return false;
    }

    /**
     * Performs proxy.
     * @param handler the handler value
     * @param otherTypes the otherTypes value
     * @return the result of proxy
     */
    public T proxy(InvocationHandler handler, Class<?>...  otherTypes){
        if (!engine.canProxy(this.type)) {
            throw new ReflectionException("Is not possible to proxy " + this.type.getName());
        }
        try {
            return engine.proxy(this.type, otherTypes, handler);
        } catch (Exception e){
            throw new ReflectionException("Is not possible to proxy " + this.type.getName());
        }
    }
}
