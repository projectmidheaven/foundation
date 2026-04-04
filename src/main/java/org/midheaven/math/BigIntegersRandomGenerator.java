package org.midheaven.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Generator for Big Integers Random values.
 */
public final class BigIntegersRandomGenerator extends AbstractDiscreteIntervalRandomGenerator<BigInteger> implements IntervalRandomGenerator<BigInteger>{


    BigIntegersRandomGenerator(Supplier<Random> base) {
        super(base);
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
    public BigInteger next() {
        return new BigInteger(64, this.base());
    }

    /**
     * Returns next Including.
     * @param upperBound the upperBound value
     * @return the result of nextIncluding
     */
    @Override
    /**
     * Returns next Including.
     * @param upperBound the upperBound value
     * @return the result of nextIncluding
     */
    protected BigInteger nextIncluding(BigInteger upperBound) {
        return next(upperBound.add(BigInteger.ONE));
    }

    /**
     * Returns next Including.
     * @param lowerBound the lowerBound value
     * @param upperBound the upperBound value
     * @return the result of nextIncluding
     */
    @Override
    /**
     * Returns next Including.
     * @param lowerBound the lowerBound value
     * @param upperBound the upperBound value
     * @return the result of nextIncluding
     */
    protected BigInteger nextIncluding(BigInteger lowerBound, BigInteger upperBound) {
        return next(lowerBound, upperBound.add(BigInteger.ONE));
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
    protected BigInteger next(BigInteger upperBound) {
        var bigUpperBound = new BigDecimal(upperBound);

        int precision = bigUpperBound.precision() - bigUpperBound.scale();
        var range = new BigDecimal(new BigInteger( 64, base())).movePointLeft(precision);

        return bigUpperBound.multiply(range , new MathContext(precision + 1)).toBigInteger();
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
    protected BigInteger next(BigInteger lowerBound, BigInteger upperBound) {
        return next(upperBound.subtract(lowerBound)).add(lowerBound);
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
    protected int compare(BigInteger a, BigInteger b) {
        return a.compareTo(b);
    }
}
