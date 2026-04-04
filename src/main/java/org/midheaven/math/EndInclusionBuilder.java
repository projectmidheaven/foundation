package org.midheaven.math;

/**
 * Builder for End Inclusion instances.
 */
public class EndInclusionBuilder<T, U> {

    private final EndIntervalBuilder<T, U> endIntervalBuilder;
    private final U maximum;

    /**
     * Creates a new EndInclusionBuilder.
     * @param endIntervalBuilder the endIntervalBuilder value
     * @param maximum the maximum value
     */
    public EndInclusionBuilder(EndIntervalBuilder<T, U> endIntervalBuilder,  U maximum) {
        this.endIntervalBuilder = endIntervalBuilder;
        this.maximum = maximum;
    }

    /**
     * Performs inclusive.
     * @return the result of inclusive
     */
    public Interval<T> inclusive(){
        var domain =  this.endIntervalBuilder.intervalBuilder.domain;
        return new BoundaryInterval<>(
            domain,
            new ActualBoundary<>(domain, true, endIntervalBuilder.isOpen,  endIntervalBuilder.minimum),
            new ActualBoundary<>(domain, false, false, maximum)
        ).reduce();
    }

    /**
     * Performs exclusive.
     * @return the result of exclusive
     */
    public Interval<T> exclusive(){
        var domain =  this.endIntervalBuilder.intervalBuilder.domain;
        return new BoundaryInterval<>(
            domain,
            new ActualBoundary<>(domain, true, endIntervalBuilder.isOpen, endIntervalBuilder.minimum),
            new ActualBoundary<>(domain, false, true, maximum)
        ).reduce();
    }
}
