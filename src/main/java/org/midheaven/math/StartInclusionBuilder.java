package org.midheaven.math;

public class StartInclusionBuilder<T, U> {

    private final IntervalBuilder<T, U> intervalBuilder;
    private final U minimum;

    StartInclusionBuilder(IntervalBuilder<T, U> intervalBuilder, U minimum) {
        this.intervalBuilder = intervalBuilder;
        this.minimum = minimum;
    }

    public EndIntervalBuilder<T, U> inclusive(){
        return new EndIntervalBuilder<>(this.intervalBuilder, minimum, false);
    }

    public EndIntervalBuilder<T, U> exclusive(){
        return new EndIntervalBuilder<>(this.intervalBuilder, minimum, true);
    }
}
