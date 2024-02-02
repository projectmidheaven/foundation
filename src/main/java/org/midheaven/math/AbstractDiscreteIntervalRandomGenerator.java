package org.midheaven.math;

import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

public abstract class AbstractDiscreteIntervalRandomGenerator<T>
        extends AbstractIntervalRandomGenerator<T>
        implements DiscreteIntervalRandomGenerator<T>
{

    AbstractDiscreteIntervalRandomGenerator(Supplier<Random> base) {
        super(base);
    }

    protected abstract T nextIncluding(T upperBound);

    protected abstract T nextIncluding(T lowerBound, T upperBound);

    @Override
    public RandomGenerator<T> upToIncluding(T upperBound) {
        Objects.requireNonNull(upperBound);
        return () -> nextIncluding(upperBound);
    }

    @Override
    public RandomGenerator<T> betweenIncluding(T lowerBound, T upperBound) {
        var comparison = checkBounds(lowerBound, upperBound);
        if (comparison == 0) {
            return () -> lowerBound;
        }
        return () -> nextIncluding(lowerBound, upperBound);
    }

}
