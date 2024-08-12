package org.midheaven.math;

import org.midheaven.lang.NotNull;

public interface AdditionMonoid<T extends AdditionMonoid<T>> {

    boolean isZero();
    @NotNull  T plus(@NotNull T other);
}
