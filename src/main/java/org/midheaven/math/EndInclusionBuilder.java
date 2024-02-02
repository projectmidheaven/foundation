package org.midheaven.math;

public class EndInclusionBuilder<T> {

    private final EndIntervalBuilder<T> endIntervalBuilder;
    private final T maximum;

    public EndInclusionBuilder(EndIntervalBuilder<T> endIntervalBuilder,  T maximum) {
        this.endIntervalBuilder = endIntervalBuilder;
        this.maximum = maximum;
    }

    public Interval<T> inclusive(){
        return new BoundaryInterval<>(
                this.endIntervalBuilder.intervalBuilder.comparator,
                new ActualBoundary<>(this.endIntervalBuilder.intervalBuilder.comparator, true, endIntervalBuilder.isOpen, endIntervalBuilder.minimum),
                new ActualBoundary<>(this.endIntervalBuilder.intervalBuilder.comparator, false, false, maximum)
        );
    }

    public Interval<T> exclusive(){
        return new BoundaryInterval<>(
                this.endIntervalBuilder.intervalBuilder.comparator,
                new ActualBoundary<>(this.endIntervalBuilder.intervalBuilder.comparator, true, endIntervalBuilder.isOpen, endIntervalBuilder.minimum),
                new ActualBoundary<>(this.endIntervalBuilder.intervalBuilder.comparator, false, true, maximum)
        ).reduce();
    }
}
