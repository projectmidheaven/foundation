package org.midheaven.math;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class LongsRandomGenerator extends AbstractDiscreteIntervalRandomGenerator<Long> implements IntervalRandomGenerator<Long>{


    LongsRandomGenerator(Supplier<Random> base) {
        super(base);
    }

    @Override
    protected Long nextIncluding(Long upperBound) {
        return next(upperBound + 1);
    }

    @Override
    protected Long nextIncluding(Long lowerBound, Long upperBound) {
        return next(lowerBound, upperBound + 1);
    }

    @Override
    protected Long next(Long upperBound) {
        return base().nextLong(upperBound);
    }

    @Override
    protected Long next(Long lowerBound, Long upperBound) {
        return base().nextLong(lowerBound,upperBound);
    }

    @Override
    protected int compare(Long a, Long b) {
        return a.compareTo(b);
    }

    @Override
    public Long next() {
        return base().nextLong();
    }

    @Override
    public Stream<Long> stream() {
        return base().longs().boxed();
    }

}
