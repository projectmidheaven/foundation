package org.midheaven.math;

public interface AdditionMonoid<T extends AdditionMonoid<T>> {

    boolean isZero();
    T plus(T other);
}
