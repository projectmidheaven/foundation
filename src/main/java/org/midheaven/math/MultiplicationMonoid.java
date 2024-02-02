package org.midheaven.math;

public interface MultiplicationMonoid<T extends MultiplicationMonoid<T>> {

    boolean isOne();
    T times(T other);
}
