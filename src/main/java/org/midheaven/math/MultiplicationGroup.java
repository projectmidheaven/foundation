package org.midheaven.math;

import org.midheaven.lang.NotNullable;

/**
 * Defines the contract for Multiplication Group.
 * @param <T> the type of element in the MultiplicationGroup
 */
public interface MultiplicationGroup<T extends MultiplicationGroup<T>> extends MultiplicationMonoid<T> {
    
    @NotNullable T invert();
    @NotNullable T over(@NotNullable T other);
}
