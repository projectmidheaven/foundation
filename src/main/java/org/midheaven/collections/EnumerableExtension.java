package org.midheaven.collections;

/**
 * Defines the contract for Enumerable Extension.
 * @param <T> the type of Elements in the Enumerable
 * @param <E> the type of the decorating Enumerable
 */
public interface EnumerableExtension<T, E extends Enumerable<T>>{

    /**
     * Creates a decorator of {@code Enumerable} with specific methods.
     * @param previous the previous enumerable to decorate
     * @return the decorated {@code Enumerable}
     */
    E extend(Enumerable<T> previous);

}
