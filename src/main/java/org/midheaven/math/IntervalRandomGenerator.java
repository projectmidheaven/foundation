package org.midheaven.math;

public interface IntervalRandomGenerator<T> extends RandomGenerator<T>{

    RandomGenerator<T> upTo(T upperBound);
    RandomGenerator<T> between(T lowerBound, T upperBound);
}
