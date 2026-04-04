package org.midheaven.collections;

import java.util.Iterator;

/**
 * Base implementation for a decorator of Enumerable.
 * See {@link org.midheaven.math.ArithmeticalEnumerable} for an example
 * @param <T> type of element in the Enumerable
 */
public abstract class AbstractEnumerableDecorator<T> implements Enumerable<T> {

    private final Enumerable<T> original;

    /**
     * Creates a new AbstractEnumerableDecorator.
     * @param original the original value
     */
    public AbstractEnumerableDecorator(Enumerable<T> original){
        this.original = original;
    }

    /**
     * Returns an enumerator over the elements.
     * @return the resulting enumerator
     */
    @Override
    public final Enumerator<T> enumerator() {
        return original.enumerator();
    }

    /**
     * Returns an iterator over the elements.
     * @return the resulting iterator
     */
    @Override
    public final Iterator<T> iterator() {
        return original.iterator();
    }
}
