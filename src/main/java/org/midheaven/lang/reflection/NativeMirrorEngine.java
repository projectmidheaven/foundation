package org.midheaven.lang.reflection;

import java.lang.reflect.Proxy;

public class NativeMirrorEngine implements ProxyEngine {

    @Override
    public <T> boolean canProxy(Class<T> type) {
        return type.isInterface();
    }

    @Override
    public <T> T proxy(Class<T> type, Class<?>[] otherTypes, InvocationHandler handler) {
        if(!type.isInterface()){
            throw new ReflectionException("Is only possible to proxy interfaces");
        }
        Class<?>[] typesToImplement = new Class[otherTypes.length + 1];
        typesToImplement[0] = type;
        if (otherTypes.length > 0){
            System.arraycopy(otherTypes, 0, typesToImplement, 1, otherTypes.length);
        }

        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), typesToImplement, handler::handleInvocation));
    }
}
