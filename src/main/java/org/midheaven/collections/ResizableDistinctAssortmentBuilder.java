package org.midheaven.collections;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Builder for Resizable Distinct Assortment instances.
 */
public class ResizableDistinctAssortmentBuilder {

    /**
     * Returns an empty instance.
     * @return the result of empty
     */
    public <T> ResizableDistinctAssortment<T> empty() {
        return new ResizableSetWrapper<>(new HashSet<>());
    }

    /**
     * Creates an instance from the provided value.
     * @param values the values value
     * @return the result of of
     */
    @SafeVarargs
    /**
     * Creates an instance from the provided value.
     * @param values the values value
     * @return the result of of
     */
    public final <T> ResizableDistinctAssortment<T> of(T... values) {
        return from(Arrays.asList(values));
    }

    /**
     * Creates an instance from the provided source.
     * @param origin the origin value
     * @return the result of from
     */
    public <T> ResizableDistinctAssortment<T> from(Iterable<T> origin) {
        if (!origin.iterator().hasNext() ) {
            return empty();
        }

        return AssortmentSupport.<T, HashSet<T> , ResizableDistinctAssortment<T>>from(
                origin,
                HashSet::new,
                ResizableSetWrapper::new
        );
    }
}
