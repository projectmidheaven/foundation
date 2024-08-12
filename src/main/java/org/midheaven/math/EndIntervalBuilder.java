package org.midheaven.math;

public class EndIntervalBuilder<T> {

    final IntervalBuilder<T> intervalBuilder;
    final T minimum;
    final boolean isOpen;

    public EndIntervalBuilder(IntervalBuilder<T> intervalBuilder, T minimum, boolean isOpen) {
        this.intervalBuilder = intervalBuilder;
        this.minimum = minimum;
        this.isOpen = isOpen;
    }

    public EndInclusionBuilder<T> to(T maximum){
        return new EndInclusionBuilder<>(this, maximum);
    }

    public Interval<T> toInfinity(){
        return new BoundaryInterval<>(
                this.intervalBuilder.comparator,
                new ActualBoundary<>(this.intervalBuilder.comparator, true, isOpen, minimum),
                new ActualBoundary<>(this.intervalBuilder.comparator, false, false, null)
        );
    }
}
