package org.midheaven.math;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class LocalDatesRandomGenerator
        extends AbstractDiscreteIntervalRandomGenerator<LocalDate>
        implements IntervalRandomGenerator<LocalDate>
{


    LocalDatesRandomGenerator(Supplier<Random> base) {
        super(base);
    }

    @Override
    protected int compare(LocalDate a, LocalDate b) {
        return a.compareTo(b);
    }

    @Override
    protected LocalDate nextIncluding(LocalDate upperBound) {
        return next(upperBound.plusDays(1));
    }

    @Override
    protected LocalDate nextIncluding(LocalDate lowerBound, LocalDate upperBound) {
        return next(lowerBound, upperBound.plusDays(1));
    }

    @Override
    protected LocalDate next(LocalDate upperBound) {
        return LocalDate.ofEpochDay(base().nextLong(0, upperBound.toEpochDay()));
    }

    @Override
    protected LocalDate next(LocalDate lowerBound, LocalDate upperBound) {
        var a = lowerBound.toEpochDay();
        var b = upperBound.toEpochDay();
        return LocalDate.ofEpochDay(base().nextLong(a, b));
    }

    @Override
    public LocalDate next() {
        return LocalDate.ofEpochDay(base().nextLong());
    }

}
