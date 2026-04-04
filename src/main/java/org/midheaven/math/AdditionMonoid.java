package org.midheaven.math;

import org.midheaven.lang.NotNullable;

/**
 * Defines the contract for Addition Monoid.
 * @param <T> the type of element in the Monoid
 */
public interface AdditionMonoid<T extends AdditionMonoid<T>> {

    /**
     * Checks whether is Zero.
     * @return the result of isZero
     */
    boolean isZero();
    @NotNullable T plus(@NotNullable T other);
}
