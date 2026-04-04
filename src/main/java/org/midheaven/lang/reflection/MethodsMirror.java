package org.midheaven.lang.reflection;

/**
 * Defines the contract for Methods Mirror.
 * @param <T> type with methods
 */
public interface MethodsMirror<T> {

    /**
     * Performs declared.
     * @return the result of declared
     */
    MethodMirrorQuery declared();

    /**
     * Performs contracted.
     * @return the result of contracted
     */
    MethodMirrorQuery contracted();
}
