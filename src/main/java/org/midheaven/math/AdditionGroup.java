package org.midheaven.math;

import org.midheaven.lang.NotNull;

public interface AdditionGroup<T extends AdditionGroup<T>> extends AdditionMonoid<T>{

    @NotNull T negate();
    @NotNull T minus(@NotNull T other);
    @NotNull T abs();
}
