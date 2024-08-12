package org.midheaven.math;

import org.midheaven.collections.AbstractEnumerableDecorator;
import org.midheaven.collections.Enumerable;

public class ArithmeticalEnumerable<N,S> extends AbstractEnumerableDecorator<N> {

    private final Arithmetic<N, S> arithmetic;

    public ArithmeticalEnumerable(Arithmetic<N, S> arithmetic, Enumerable<N> original) {
        super(original);
        this.arithmetic = arithmetic;
    }

    public N sum(){
        return this.collect(arithmetic.sumCollector());
    }

    public S average(){
        return this.collect(arithmetic.averageCollector());
    }
}