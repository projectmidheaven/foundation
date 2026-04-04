package org.midheaven.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Generator for Big Decimals Random values.
 */
public final class BigDecimalsRandomGenerator extends AbstractIntervalRandomGenerator<BigDecimal> implements IntervalRandomGenerator<BigDecimal>{


    BigDecimalsRandomGenerator(Supplier<Random> base) {
        super(base);
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
    protected int compare(BigDecimal a, BigDecimal b) {
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
    public BigDecimal next() {
        var value = new BigDecimal(new BigInteger( 64, base()));
        return value.movePointLeft(value.precision()); // move all the way to make the number 0.xxx
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
    protected BigDecimal next(BigDecimal upperBound) {
        return next(BigDecimal.ZERO, upperBound);
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
    protected BigDecimal next(BigDecimal lowerBound, BigDecimal upperBound) {
        int digitCount = Math.max(lowerBound.precision(), upperBound.precision());
        int bitCount = (int)(digitCount / Math.log10(2.0));

        BigDecimal range = new BigDecimal(new BigInteger(bitCount, base())).movePointLeft(digitCount);

        return upperBound.subtract(lowerBound).multiply(range, new MathContext(digitCount)).add(lowerBound);
    }



}
