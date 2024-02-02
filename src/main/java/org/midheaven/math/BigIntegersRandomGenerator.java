package org.midheaven.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class BigIntegersRandomGenerator extends AbstractDiscreteIntervalRandomGenerator<BigInteger> implements IntervalRandomGenerator<BigInteger>{


    BigIntegersRandomGenerator(Supplier<Random> base) {
        super(base);
    }

    @Override
    public BigInteger next() {
        return new BigInteger(64, this.base());
    }

    @Override
    protected BigInteger nextIncluding(BigInteger upperBound) {
        return next(upperBound.add(BigInteger.ONE));
    }

    @Override
    protected BigInteger nextIncluding(BigInteger lowerBound, BigInteger upperBound) {
        return next(lowerBound, upperBound.add(BigInteger.ONE));
    }

    @Override
    protected BigInteger next(BigInteger upperBound) {
        var bigUpperBound = new BigDecimal(upperBound);

        int precision = bigUpperBound.precision() - bigUpperBound.scale();
        var range = new BigDecimal(new BigInteger( 64, base())).movePointLeft(precision);

        return bigUpperBound.multiply(range , new MathContext(precision + 1)).toBigInteger();
    }

    @Override
    protected BigInteger next(BigInteger lowerBound, BigInteger upperBound) {
        return next(upperBound.subtract(lowerBound)).add(lowerBound);
    }

    @Override
    protected int compare(BigInteger a, BigInteger b) {
        return a.compareTo(b);
    }
}
