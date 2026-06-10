package org.midheaven.math;

import org.midheaven.lang.Maybe;

import java.util.Objects;

/**
 * Builder for End Interval instances.
 * @param <T> type of the elements in the {@link Interval}
 * @param <U> type of the value in the {@link Interval}
 */
public class EndIntervalBuilder<T, U> {

    final IntervalBuilder<T, U> intervalBuilder;
    final U minimum;
    final boolean isOpen;

    /**
     * Creates a new EndIntervalBuilder.
     * @param intervalBuilder the intervalBuilder value
     * @param minimum the minimum value
     * @param isOpen the isOpen value
     */
    public EndIntervalBuilder(IntervalBuilder<T, U> intervalBuilder, U minimum, boolean isOpen) {
        this.intervalBuilder = intervalBuilder;
        this.minimum = minimum;
        this.isOpen = isOpen;
    }

    /**
     * Performs to.
     * @param value the maximum value
     * @return the result of to
     */
    public EndInclusionBuilder<T, U> to(U value){
        Objects.requireNonNull(value);
        return new EndInclusionBuilder<>(this, value);
    }
    
    /**
     * Creates an instance from the provided source.
     * @param value the value
     * @return the result of from
     */
    public EndInclusionBuilder<T, U> to(Maybe<U> value){
        Objects.requireNonNull(value);
        return new EndInclusionBuilder<>(this, value.orNull());
    }
    
    
    /**
     * Returns to Infinity.
     * @return the result of toInfinity
     */
    public Interval<T> toInfinity(){
        return new BoundaryInterval<>(
                this.intervalBuilder.domain,
                new ActualBoundary<>(this.intervalBuilder.domain, true, isOpen,  minimum),
                new ActualBoundary<>(this.intervalBuilder.domain, false, true, null)
        ).reduce();
    }
}
