package org.midheaven.lang.reflection;

import org.midheaven.collections.Sequence;

import java.lang.reflect.Method;

/**
 * Defines the contract for Mirror Query.
 */
public interface MirrorQuery<T> {

    /**
     * Returns to Sequence.
     * @return the result of toSequence
     */
    Sequence<Method> toSequence();
}
