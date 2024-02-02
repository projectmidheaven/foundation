package org.midheaven.math;

import java.util.Comparator;
import java.util.Objects;

public class IntervalBuilder<T> {

    final Comparator<T> comparator;

    IntervalBuilder(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    Interval<T> between(T min , T max){
        Objects.requireNonNull(min);
        Objects.requireNonNull(max);
        return from(min).inclusive().to(max).inclusive();
    }

    public StartInclusionBuilder<T> from(T value){
        Objects.requireNonNull(value);
        return new StartInclusionBuilder<>(this, value);
    }

    public EndIntervalBuilder<T> fromInfinity(){
        return new EndIntervalBuilder<>(this, null, true);
    }
}
