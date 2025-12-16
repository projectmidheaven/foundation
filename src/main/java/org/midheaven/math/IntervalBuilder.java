package org.midheaven.math;

import java.util.Objects;

public class IntervalBuilder<T, U> {

    final Interval.Domain<T, U> domain;

    IntervalBuilder(Interval.Domain<T, U> domain) {
        this.domain = domain;
    }

    Interval<T> between(U min , U max){
        Objects.requireNonNull(min);
        Objects.requireNonNull(max);
        return from(min).inclusive().to(max).inclusive();
    }
    
    Interval<T> at(U value){
        Objects.requireNonNull(value);
        return from(value).inclusive().to(value).inclusive();
    }

    public StartInclusionBuilder<T, U> from(U value){
        Objects.requireNonNull(value);
        return new StartInclusionBuilder<>(this, value);
    }

    public EndIntervalBuilder<T, U> fromInfinity(){
        return new EndIntervalBuilder<>(this, null, true);
    }
}
