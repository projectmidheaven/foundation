package org.midheaven.collections;

/**
 * Defines the contract for Enumerable Extension.
 */
public interface EnumerableExtension<T, E extends Enumerable<T>>{

    /**
     * Creates a decorator of {@code Enumerable} with specific methods.
     * @param previous the previous enumerable to decorate
     * @return the decorated {@code Enumerable}
     */
    E extend(Enumerable<T> previous);

}
