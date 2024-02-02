package org.midheaven.math;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class DoublesRandomGenerator extends AbstractIntervalRandomGenerator<Double>{

    DoublesRandomGenerator(Supplier<Random> base) {
        super(base);
    }

    @Override
    protected int compare(Double a, Double b) {
        return a.compareTo(b);
    }

    @Override
    public Double next() {
        return base().nextDouble();
    }

    protected Double next(Double upperBound) {
        return base().nextDouble(upperBound);
    }

    protected Double next(Double lowerBound, Double upperBound) {
        return base().nextDouble(lowerBound,upperBound);
    }

    @Override
    public Stream<Double> stream() {
        return base().doubles().boxed();
    }


}
