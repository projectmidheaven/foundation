package org.midheaven.math;

public class StartInclusionBuilder<T> {

    private final IntervalBuilder<T> intervalBuilder;
    private final T minimum;

    StartInclusionBuilder(IntervalBuilder<T> intervalBuilder, T minimum) {
        this.intervalBuilder = intervalBuilder;
        this.minimum = minimum;
    }

    public EndIntervalBuilder<T> inclusive(){
        return new EndIntervalBuilder<>(this.intervalBuilder, minimum, false);
    }

    public EndIntervalBuilder<T> exclusive(){
        return new EndIntervalBuilder<>(this.intervalBuilder, minimum, true);
    }
}
