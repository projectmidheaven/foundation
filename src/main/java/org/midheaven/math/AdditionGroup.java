package org.midheaven.math;

import org.midheaven.lang.Nullable;

public interface AdditionGroup<T extends AdditionGroup<T>> extends AdditionMonoid<T>{

    @Nullable
    T negate();
    @Nullable
    T minus(@Nullable T other);
    @Nullable
    T abs();
}
