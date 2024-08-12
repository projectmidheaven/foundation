package org.midheaven.lang.reflection;

public interface MethodsMirror<T> {

    MethodMirrorQuery declared();

    MethodMirrorQuery contracted();
}
