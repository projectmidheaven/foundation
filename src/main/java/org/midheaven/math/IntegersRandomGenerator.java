package org.midheaven.math;

import org.midheaven.keys.Concept;
import org.midheaven.keys.Key;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class IntegersRandomGenerator extends AbstractDiscreteIntervalRandomGenerator<Integer> implements IntervalRandomGenerator<Integer>{


    IntegersRandomGenerator(Supplier<Random> base) {
        super(base);
    }

    @Override
    protected Integer nextIncluding(Integer upperBound) {
        return next(upperBound + 1);
    }

    @Override
    protected Integer nextIncluding(Integer lowerBound, Integer upperBound) {
        return next(lowerBound, upperBound + 1);
    }

    @Override
    protected Integer next(Integer upperBound) {
        return base().nextInt(upperBound);
    }

    @Override
    protected Integer next(Integer lowerBound, Integer upperBound) {
        return base().nextInt(lowerBound,upperBound);
    }

    @Override
    protected int compare(Integer a, Integer b) {
        return a.compareTo(b);
    }

    @Override
    public Integer next() {
        return base().nextInt();
    }

    @Override
    public Stream<Integer> stream() {
        return base().ints().boxed();
    }


}
