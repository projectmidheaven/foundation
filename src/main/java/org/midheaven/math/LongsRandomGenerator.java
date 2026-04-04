package org.midheaven.math;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Generator for Longs Random values.
 */
public final class LongsRandomGenerator extends AbstractDiscreteIntervalRandomGenerator<Long> implements IntervalRandomGenerator<Long>{


    LongsRandomGenerator(Supplier<Random> base) {
        super(base);
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
    protected Long nextIncluding(Long upperBound) {
        return next(upperBound + 1);
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
    protected Long nextIncluding(Long lowerBound, Long upperBound) {
        return next(lowerBound, upperBound + 1);
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
    protected Long next(Long upperBound) {
        return base().nextLong(upperBound);
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
    protected Long next(Long lowerBound, Long upperBound) {
        return base().nextLong(lowerBound,upperBound);
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
    protected int compare(Long a, Long b) {
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
    public Long next() {
        return base().nextLong();
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
    public Stream<Long> stream() {
        return base().longs().boxed();
    }

}
