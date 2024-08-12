package org.midheaven.math;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public final class ArithmeticAccumulatorCollector<T, S> implements Collector<T, ArithmeticAccumulator<T, S>, S> {

    private final ArithmeticAccumulator<T, S> accumulator;

    public ArithmeticAccumulatorCollector(ArithmeticAccumulator<T, S> accumulator) {
        this.accumulator = accumulator;
    }

    @Override
    public Supplier<ArithmeticAccumulator<T, S>> supplier() {
        return accumulator::newInstance;
    }

    @Override
    public BiConsumer<ArithmeticAccumulator<T, S>, T> accumulator() {
        return ArithmeticAccumulator::accumulate;
    }

    @Override
    public BinaryOperator<ArithmeticAccumulator<T, S>> combiner() {
        return ArithmeticAccumulator::combine;
    }

    @Override
    public Function<ArithmeticAccumulator<T, S>, S> finisher() {
        return ArithmeticAccumulator::result;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}
