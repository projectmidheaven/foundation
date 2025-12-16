package org.midheaven.math;

import org.midheaven.lang.Nullable;

public interface MultiplicationMonoid<T extends MultiplicationMonoid<T>> {

    boolean isOne();
    @Nullable
    T times(@Nullable T other);
}
