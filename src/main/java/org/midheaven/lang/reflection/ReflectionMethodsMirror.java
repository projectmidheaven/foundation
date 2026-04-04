package org.midheaven.lang.reflection;

import org.midheaven.collections.Array;

/**
 * Represents Reflection Methods Mirror.
 */
public class ReflectionMethodsMirror<T> implements MethodsMirror<T> {

    private final Class<T> type;

    /**
     * Creates a new ReflectionMethodsMirror.
     * @param type the type value
     */
    public ReflectionMethodsMirror(Class<T> type) {
        this.type = type;
    }

    /**
     * Performs declared.
     * @return the result of declared
     */
    @Override
    /**
     * Performs declared.
     * @return the result of declared
     */
    public MethodMirrorQuery declared() {
        return new MethodMirrorQuery(Array.of(type.getDeclaredMethods()));
    }

    /**
     * Performs contracted.
     * @return the result of contracted
     */
    @Override
    /**
     * Performs contracted.
     * @return the result of contracted
     */
    public MethodMirrorQuery contracted() {
        return new MethodMirrorQuery(Array.of(type.getMethods()));
    }
}
