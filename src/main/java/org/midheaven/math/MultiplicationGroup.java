package org.midheaven.math;

public interface MultiplicationGroup<T extends MultiplicationGroup<T>> extends MultiplicationMonoid<T> {

    T invert();
    T over(T other);
}
