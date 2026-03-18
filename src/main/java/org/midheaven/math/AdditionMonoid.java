package org.midheaven.math;

import org.midheaven.lang.NotNullable;

public interface AdditionMonoid<T extends AdditionMonoid<T>> {

    boolean isZero();
    @NotNullable T plus(@NotNullable T other);
}
