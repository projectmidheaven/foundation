package org.midheaven.math;

import org.midheaven.lang.Nullable;

import java.util.function.BiFunction;

/**
 * Immutable record for Identity Domain.
 */
record IdentityDomain<T>(BiFunction<T, T, Integer> comparator) implements Interval.Domain<T, T> {
    
    @Override
    public int compare(T a, T b) {
        return comparator.apply(a,b);
    }
    
    @Nullable
    @Override
    public T applyMinimum(@Nullable T minimum) {
        return minimum;
    }
    
    @Nullable
    @Override
    public T applyMaximum(@Nullable T maximum) {
        return maximum;
    }
}
