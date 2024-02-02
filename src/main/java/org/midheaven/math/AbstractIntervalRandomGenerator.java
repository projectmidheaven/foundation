package org.midheaven.math;

import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

public abstract class AbstractIntervalRandomGenerator<T>
        extends DelegatingRandomGenerator<T>
        implements IntervalRandomGenerator<T>
{

    AbstractIntervalRandomGenerator(Supplier<Random> base) {
        super(base);
    }

    protected abstract T next(T upperBound);

    protected abstract T next(T lowerBound, T upperBound);

    @Override
    public RandomGenerator<T> upTo(T upperBound) {
        Objects.requireNonNull(upperBound);
        return () -> next(upperBound);
    }

    @Override
    public RandomGenerator<T> between(T lowerBound, T upperBound) {
        var comparison = checkBounds(lowerBound, upperBound);
        if (comparison == 0) {
            return () -> lowerBound;
        }
        return () -> next(lowerBound, upperBound);
    }

    protected final int checkBounds(T lowerBound, T upperBound){
        Objects.requireNonNull(lowerBound);
        Objects.requireNonNull(upperBound);
        var comparison = compare(upperBound, lowerBound);
        if (comparison < 0){
            throw new IllegalArgumentException("Upper bound must be greater or equal to lower bound");
        }
        return comparison;
    }

    protected abstract int compare(T a, T b);
}
