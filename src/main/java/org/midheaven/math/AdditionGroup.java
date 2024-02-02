package org.midheaven.math;

public interface AdditionGroup<T extends AdditionGroup<T>> extends AdditionMonoid<T>{

    T negate();
    T minus(T other);
    T abs();
}
