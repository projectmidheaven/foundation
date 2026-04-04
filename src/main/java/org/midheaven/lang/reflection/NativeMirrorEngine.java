package org.midheaven.lang.reflection;

import java.lang.reflect.Proxy;

/**
 * Represents Native Mirror Engine.
 */
public class NativeMirrorEngine implements ProxyEngine {

    /**
     * Checks whether can Proxy.
     * @param type the type value
     * @return the result of canProxy
     */
    @Override
    /**
     * Checks whether can Proxy.
     * @param type the type value
     * @return the result of canProxy
     */
    public <T> boolean canProxy(Class<T> type) {
        return type.isInterface();
    }

    /**
     * Performs proxy.
     * @param type the type value
     * @param otherTypes the otherTypes value
     * @param handler the handler value
     * @return the result of proxy
     */
    @Override
    /**
     * Performs proxy.
     * @param type the type value
     * @param otherTypes the otherTypes value
     * @param handler the handler value
     * @return the result of proxy
     */
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
