package org.midheaven.math;

import org.midheaven.lang.NotNull;

public interface MultiplicationMonoid<T extends MultiplicationMonoid<T>> {

    boolean isOne();
    @NotNull T times(@NotNull T other);
}
