package org.midheaven.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Random;
import java.util.function.Supplier;

public final class BigDecimalsRandomGenerator extends AbstractIntervalRandomGenerator<BigDecimal> implements IntervalRandomGenerator<BigDecimal>{


    BigDecimalsRandomGenerator(Supplier<Random> base) {
        super(base);
    }

    @Override
    protected int compare(BigDecimal a, BigDecimal b) {
        return a.compareTo(b);
    }

    @Override
    public BigDecimal next() {
        var value = new BigDecimal(new BigInteger( 64, base()));
        return value.movePointLeft(value.precision()); // move all the way to make the number 0.xxx
    }

    @Override
    protected BigDecimal next(BigDecimal upperBound) {
        return next(BigDecimal.ZERO, upperBound);
    }

    @Override
    protected BigDecimal next(BigDecimal lowerBound, BigDecimal upperBound) {
        int digitCount = Math.max(lowerBound.precision(), upperBound.precision());
        int bitCount = (int)(digitCount / Math.log10(2.0));

        BigDecimal range = new BigDecimal(new BigInteger(bitCount, base())).movePointLeft(digitCount);

        return upperBound.subtract(lowerBound).multiply(range, new MathContext(digitCount)).add(lowerBound);
    }



}
