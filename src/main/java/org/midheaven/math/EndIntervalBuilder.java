package org.midheaven.math;

public class EndIntervalBuilder<T, U> {

    final IntervalBuilder<T, U> intervalBuilder;
    final U minimum;
    final boolean isOpen;

    public EndIntervalBuilder(IntervalBuilder<T, U> intervalBuilder, U minimum, boolean isOpen) {
        this.intervalBuilder = intervalBuilder;
        this.minimum = minimum;
        this.isOpen = isOpen;
    }

    public EndInclusionBuilder<T, U> to(U maximum){
        return new EndInclusionBuilder<>(this, maximum);
    }

    public Interval<T> toInfinity(){
        return new BoundaryInterval<>(
                this.intervalBuilder.domain,
                new ActualBoundary<>(this.intervalBuilder.domain, true, isOpen,  minimum),
                new ActualBoundary<>(this.intervalBuilder.domain, false, true, null)
        ).reduce();
    }
}
