package org.midheaven.math;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Generator for Rational Random values.
 */
public class RationalRandomGenerator extends AbstractIntervalRandomGenerator<Rational>{

    RationalRandomGenerator(Supplier<Random> base) {
        super(base);
    }

    /**
     * Performs next.
     * @param upperBound the upperBound value
     * @return the result of next
     */
    @Override
    /**
     * Performs next.
     * @param upperBound the upperBound value
     * @return the result of next
     */
    protected Rational next(Rational upperBound) {
        return next().times(upperBound);
    }

    /**
     * Performs next.
     * @param lowerBound the lowerBound value
     * @param upperBound the upperBound value
     * @return the result of next
     */
    @Override
    /**
     * Performs next.
     * @param lowerBound the lowerBound value
     * @param upperBound the upperBound value
     * @return the result of next
     */
    protected Rational next(Rational lowerBound, Rational upperBound) {
        return lowerBound.plus(next().times(upperBound.minus(lowerBound)));
    }

    /**
     * Performs compare.
     * @param a the a value
     * @param b the b value
     * @return the result of compare
     */
    @Override
    /**
     * Performs compare.
     * @param a the a value
     * @param b the b value
     * @return the result of compare
     */
    protected int compare(Rational a, Rational b) {
        return a.compareTo(b);
    }

    /**
     * Performs next.
     * @return the result of next
     */
    @Override
    /**
     * Performs next.
     * @return the result of next
     */
    public Rational next() {
        return Rational.of(base().nextLong(0, Long.MAX_VALUE - 1), Long.MAX_VALUE);
    }
}
