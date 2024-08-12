package org.midheaven.lang.reflection;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ByteBuddyProxyEngine implements ProxyEngine {
    @Override
    public <T> boolean canProxy(Class<T> type) {
        return !type.isPrimitive()
                && !type.isRecord()
                && !type.isEnum()
                && !type.isSealed();
    }

    @Override
    public <T> T proxy(Class<T> type,Class<?>[]  interfacesTypes, InvocationHandler handler) {
        if (Arrays.stream(interfacesTypes).anyMatch(it -> !it.isInterface())){
            throw new IllegalArgumentException("Additional types must all be interfaces");
        }
        try (var maker = new ByteBuddy()
                .subclass(type)
                .implement(interfacesTypes)
                .method(ElementMatchers.any())
                .intercept(InvocationHandlerAdapter.of(handler::handleInvocation))
                .make()){
            return maker.load(type.getClassLoader())
                    .getLoaded().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new ReflectionException(e);
        }

    }
}
