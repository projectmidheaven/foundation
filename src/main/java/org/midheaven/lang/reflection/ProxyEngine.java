package org.midheaven.lang.reflection;


public interface ProxyEngine {

    <T> boolean canProxy(Class<T> type);

    <T> T proxy(Class<T> type, Class<?>[] otherTypes, InvocationHandler handler);
}
