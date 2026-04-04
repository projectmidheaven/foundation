package org.midheaven.math;

import org.midheaven.lang.NotNullable;

/**
 * Defines the contract for Addition Group.
 * @param <T> the type of element in the group
 */
public interface AdditionGroup<T extends AdditionGroup<T>> extends AdditionMonoid<T>{

    @NotNullable T negate();
    @NotNullable T minus(@NotNullable T other);
    @NotNullable T abs();
}
