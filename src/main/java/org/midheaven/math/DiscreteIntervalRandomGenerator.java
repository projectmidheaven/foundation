package org.midheaven.math;

public interface DiscreteIntervalRandomGenerator<T> extends IntervalRandomGenerator<T>{

    RandomGenerator<T> upToIncluding(T upperBound);
    RandomGenerator<T> betweenIncluding(T lowerBound, T upperBound);
}
