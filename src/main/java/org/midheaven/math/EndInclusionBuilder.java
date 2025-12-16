package org.midheaven.math;

public class EndInclusionBuilder<T, U> {

    private final EndIntervalBuilder<T, U> endIntervalBuilder;
    private final U maximum;

    public EndInclusionBuilder(EndIntervalBuilder<T, U> endIntervalBuilder,  U maximum) {
        this.endIntervalBuilder = endIntervalBuilder;
        this.maximum = maximum;
    }

    public Interval<T> inclusive(){
        var domain =  this.endIntervalBuilder.intervalBuilder.domain;
        return new BoundaryInterval<>(
            domain,
            new ActualBoundary<>(domain, true, endIntervalBuilder.isOpen,  endIntervalBuilder.minimum),
            new ActualBoundary<>(domain, false, false, maximum)
        ).reduce();
    }

    public Interval<T> exclusive(){
        var domain =  this.endIntervalBuilder.intervalBuilder.domain;
        return new BoundaryInterval<>(
            domain,
            new ActualBoundary<>(domain, true, endIntervalBuilder.isOpen, endIntervalBuilder.minimum),
            new ActualBoundary<>(domain, false, true, maximum)
        ).reduce();
    }
}
