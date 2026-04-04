package org.midheaven.math;

/**
 * Defines the contract for Arithmetic Accumulator.
 */
public interface ArithmeticAccumulator<T, R> {

    /**
     * Creates new Instance.
     * @return the result of newInstance
     */
    ArithmeticAccumulator<T, R> newInstance();

    /**
     * Performs accumulate.
     * @param value the value value
     */
    void accumulate(T value);

    /**
     * Performs combine.
     * @param other the other value
     * @return the result of combine
     */
    ArithmeticAccumulator<T, R> combine(ArithmeticAccumulator<T, R> other);

    /**
     * Performs result.
     * @return the result of result
     */
    R result();

}
