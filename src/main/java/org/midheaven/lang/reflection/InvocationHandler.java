package org.midheaven.lang.reflection;

import java.lang.reflect.Method;

/**
 * Defines the contract for Invocation Handler.
 */
public interface InvocationHandler {

    /**
     * Performs handleInvocation.
     * @param proxy the proxy value
     * @param method the method value
     * @param args the args value
     * @return the result of handleInvocation
     */
    Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable;
}
