package org.midheaven.math;

import org.midheaven.lang.Nullable;

public interface AdditionMonoid<T extends AdditionMonoid<T>> {

    boolean isZero();
    @Nullable
    T plus(@Nullable T other);
}
