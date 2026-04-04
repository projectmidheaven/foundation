package org.midheaven.math;

/**
 * Generator for Interval Random values.
 */
public interface IntervalRandomGenerator<T> extends RandomGenerator<T>{

    /**
     * Performs upTo.
     * @param upperBound the upperBound value
     * @return the result of upTo
     */
    RandomGenerator<T> upTo(T upperBound);
    /**
     * Performs between.
     * @param lowerBound the lowerBound value
     * @param upperBound the upperBound value
     * @return the result of between
     */
    RandomGenerator<T> between(T lowerBound, T upperBound);
}
