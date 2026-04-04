package org.midheaven.math;

import org.midheaven.collections.AbstractEnumerableDecorator;
import org.midheaven.collections.Enumerable;

/**
 * Represents Arithmetical Enumerable.
 */
public class ArithmeticalEnumerable<N,S> extends AbstractEnumerableDecorator<N> {

    private final Arithmetic<N, S> arithmetic;

    /**
     * Creates a new ArithmeticalEnumerable.
     * @param arithmetic the arithmetic value
     * @param original the original value
     */
    public ArithmeticalEnumerable(Arithmetic<N, S> arithmetic, Enumerable<N> original) {
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
    public S average(){
        return this.collect(arithmetic.averageCollector());
    }
}
