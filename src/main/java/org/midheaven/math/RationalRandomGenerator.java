package org.midheaven.math;

import java.util.Random;
import java.util.function.Supplier;

public class RationalRandomGenerator extends AbstractIntervalRandomGenerator<Rational>{

    RationalRandomGenerator(Supplier<Random> base) {
        super(base);
    }

    @Override
    protected Rational next(Rational upperBound) {
        return next().times(upperBound);
    }

    @Override
    protected Rational next(Rational lowerBound, Rational upperBound) {
        return lowerBound.plus(next().times(upperBound.minus(lowerBound)));
    }

    @Override
    protected int compare(Rational a, Rational b) {
        return a.compareTo(b);
    }

    @Override
    public Rational next() {
        return Rational.of(base().nextLong(0, Long.MAX_VALUE - 1), Long.MAX_VALUE);
    }
}
