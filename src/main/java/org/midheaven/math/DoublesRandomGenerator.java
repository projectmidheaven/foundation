package org.midheaven.math;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Generator for Doubles Random values.
 */
public final class DoublesRandomGenerator extends AbstractIntervalRandomGenerator<Double>{

    DoublesRandomGenerator(Supplier<Random> base) {
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
    protected int compare(Double a, Double b) {
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
    public Double next() {
        return base().nextDouble();
    }

    /**
     * Performs next.
     * @param upperBound the upperBound value
     * @return the result of next
     */
    protected Double next(Double upperBound) {
        return base().nextDouble(upperBound);
    }

    /**
     * Performs next.
     * @param lowerBound the lowerBound value
     * @param upperBound the upperBound value
     * @return the result of next
     */
    protected Double next(Double lowerBound, Double upperBound) {
        return base().nextDouble(lowerBound,upperBound);
    }

    /**
     * Performs stream.
     * @return the result of stream
     */
    @Override
    /**
     * Performs stream.
     * @return the result of stream
     */
    public Stream<Double> stream() {
        return base().doubles().boxed();
    }


}
