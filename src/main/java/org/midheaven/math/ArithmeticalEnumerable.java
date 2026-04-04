package org.midheaven.math;

import org.midheaven.collections.AbstractEnumerableDecorator;
import org.midheaven.collections.Enumerable;

/**
 * Represents Arithmetical Enumerable.
 * @param <N> type of the element being enumerated
 * @param <D> type of the element resulting from dividing {@code T} by a whole number
 */
public class ArithmeticalEnumerable<N, D> extends AbstractEnumerableDecorator<N> {

    private final Arithmetic<N, D> arithmetic;

    /**
     * Creates a new ArithmeticalEnumerable.
     * @param arithmetic the arithmetic value
     * @param original the original value
     */
    public ArithmeticalEnumerable(Arithmetic<N, D> arithmetic, Enumerable<N> original) {
        super(original);
        this.arithmetic = arithmetic;
    }

    /**
     * Performs sum.
     * @return the result of sum
     */
    public N sum(){
        return this.collect(arithmetic.sumCollector());
    }

    /**
     * Performs average.
     * @return the result of average
     */
    public D average(){
        return this.collect(arithmetic.averageCollector());
    }
}
