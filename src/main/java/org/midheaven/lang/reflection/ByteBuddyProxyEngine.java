package org.midheaven.lang.reflection;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;

public class ByteBuddyProxyEngine implements ProxyEngine {
    @Override
    public <T> boolean canProxy(Class<T> type) {
        return !type.isPrimitive()
                && !type.isRecord()
                && !type.isInterface()
                && !type.isEnum()
                && !type.isInterface();
    }

    @Override
    public <T> T proxy(Class<T> type,Class<?>[]  otherTypes, InvocationHandler handler) {
        try (var maker = new ByteBuddy()
                .subclass(type)
                .permittedSubclass(otherTypes)
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
