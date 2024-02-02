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
        return this.reduce(arithmetic.zero(), arithmetic::sum);
    }

    public S average(){
        var increment = this.reduce(new Increments<N>(arithmetic.zero()), (i, b) -> {
            i.sum = arithmetic.sum(i.sum, b);
            i.count++;
            return i;
        });

        return arithmetic.over( increment.sum ,  increment.count);
    }
}

class Increments<T> {
    T sum;
    int count;

    public Increments(T zero) {
        this.sum = zero;
    }
}