package org.midheaven.math;

import org.midheaven.lang.NotNullable;

/**
 * Defines the contract for Multiplication Monoid.
 * @param <T> the type of element in the MultiplicationMonoid
 */
public interface MultiplicationMonoid<T extends MultiplicationMonoid<T>> {

    /**
     * Checks whether is One.
     * @return the result of isOne
     */
    boolean isOne();
    @NotNullable T times(@NotNullable T other);
}
