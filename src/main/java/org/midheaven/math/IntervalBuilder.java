package org.midheaven.math;

import java.util.Objects;

/**
 * Builder for Interval instances.
 * @param <T> the type of element in the Interval
 * @param <U> the type of value in the Interval
 */
public class IntervalBuilder<T, U> {

    final Interval.Domain<T, U> domain;

    IntervalBuilder(Interval.Domain<T, U> domain) {
        this.domain = domain;
    }

    /**
     * Performs between.
     * @param min the min value
     * @param max the max value
     * @return the result of between
     */
    Interval<T> between(U min , U max){
        Objects.requireNonNull(min);
        Objects.requireNonNull(max);
        return from(min).inclusive().to(max).inclusive();
    }
    
    /**
     * Performs at.
     * @param value the value value
     * @return the result of at
     */
    Interval<T> at(U value){
        Objects.requireNonNull(value);
        return from(value).inclusive().to(value).inclusive();
    }

    /**
     * Creates an instance from the provided source.
     * @param value the value value
     * @return the result of from
     */
    public StartInclusionBuilder<T, U> from(U value){
        Objects.requireNonNull(value);
        return new StartInclusionBuilder<>(this, value);
    }

    /**
     * Returns from Infinity.
     * @return the result of fromInfinity
     */
    public EndIntervalBuilder<T, U> fromInfinity(){
        return new EndIntervalBuilder<>(this, null, true);
    }
}
