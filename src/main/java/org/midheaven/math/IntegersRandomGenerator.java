package org.midheaven.math;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Generator for Integers Random values.
 */
public final class IntegersRandomGenerator extends AbstractDiscreteIntervalRandomGenerator<Integer> implements IntervalRandomGenerator<Integer>{


    IntegersRandomGenerator(Supplier<Random> base) {
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
    protected Integer nextIncluding(Integer upperBound) {
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
    protected Integer nextIncluding(Integer lowerBound, Integer upperBound) {
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
    protected Integer next(Integer upperBound) {
        return base().nextInt(upperBound);
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
    protected Integer next(Integer lowerBound, Integer upperBound) {
        return base().nextInt(lowerBound,upperBound);
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
    protected int compare(Integer a, Integer b) {
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
    public Integer next() {
        return base().nextInt();
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
    public Stream<Integer> stream() {
        return base().ints().boxed();
    }


}
