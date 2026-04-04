package org.midheaven.math;

/**
 * Generator for Discrete Interval Random values.
 */
public interface DiscreteIntervalRandomGenerator<T> extends IntervalRandomGenerator<T>{

    /**
     * Performs upToIncluding.
     * @param upperBound the upperBound value
     * @return the result of upToIncluding
     */
    RandomGenerator<T> upToIncluding(T upperBound);
    /**
     * Performs betweenIncluding.
     * @param lowerBound the lowerBound value
     * @param upperBound the upperBound value
     * @return the result of betweenIncluding
     */
    RandomGenerator<T> betweenIncluding(T lowerBound, T upperBound);
}
