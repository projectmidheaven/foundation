package org.midheaven.math;

import org.midheaven.lang.NotNullable;

public interface MultiplicationGroup<T extends MultiplicationGroup<T>> extends MultiplicationMonoid<T> {
    
    @NotNullable T invert();
    @NotNullable T over(@NotNullable T other);
}
