package org.midheaven.lang.reflection;

import java.lang.reflect.Method;

public interface InvocationHandler {

    Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable;
}
