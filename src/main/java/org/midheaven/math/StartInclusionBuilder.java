package org.midheaven.math;

/**
 * Builder for Start Inclusion instances.
 * @param <T> type of the elements in the {@link Interval}
 * @param <U> type of the value in the {@link Interval}
 */
public class StartInclusionBuilder<T, U> {

    private final IntervalBuilder<T, U> intervalBuilder;
    private final U minimum;

    StartInclusionBuilder(IntervalBuilder<T, U> intervalBuilder, U minimum) {
        this.intervalBuilder = intervalBuilder;
        this.minimum = minimum;
    }

    /**
     * Performs inclusive.
     * @return the result of inclusive
     */
    public EndIntervalBuilder<T, U> inclusive(){
        return new EndIntervalBuilder<>(this.intervalBuilder, minimum, false);
    }

    /**
     * Performs exclusive.
     * @return the result of exclusive
     */
    public EndIntervalBuilder<T, U> exclusive(){
        return new EndIntervalBuilder<>(this.intervalBuilder, minimum, true);
    }
}
