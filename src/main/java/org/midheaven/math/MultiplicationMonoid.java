package org.midheaven.math;

import org.midheaven.lang.NotNullable;

public interface MultiplicationMonoid<T extends MultiplicationMonoid<T>> {

    boolean isOne();
    @NotNullable T times(@NotNullable T other);
}
