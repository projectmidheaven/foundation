package org.midheaven.math;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;


final class ArithmeticAccumulatorCollector<T, S> implements Collector<T, ArithmeticAccumulator<T, S>, S> {

    private final ArithmeticAccumulator<T, S> accumulator;

    /**
     * Creates a new ArithmeticAccumulatorCollector.
     * @param accumulator the accumulator value
     */
    public ArithmeticAccumulatorCollector(ArithmeticAccumulator<T, S> accumulator) {
        this.accumulator = accumulator;
    }

    /**
     * Performs supplier.
     * @return the result of supplier
     */
    @Override
    /**
     * Performs supplier.
     * @return the result of supplier
     */
    public Supplier<ArithmeticAccumulator<T, S>> supplier() {
        return accumulator::newInstance;
    }

    /**
     * Performs accumulator.
     * @return the result of accumulator
     */
    @Override
    /**
     * Performs accumulator.
     * @return the result of accumulator
     */
    public BiConsumer<ArithmeticAccumulator<T, S>, T> accumulator() {
        return ArithmeticAccumulator::accumulate;
    }

    /**
     * Performs combiner.
     * @return the result of combiner
     */
    @Override
    /**
     * Performs combiner.
     * @return the result of combiner
     */
    public BinaryOperator<ArithmeticAccumulator<T, S>> combiner() {
        return ArithmeticAccumulator::combine;
    }

    /**
     * Performs finisher.
     * @return the result of finisher
     */
    @Override
    /**
     * Performs finisher.
     * @return the result of finisher
     */
    public Function<ArithmeticAccumulator<T, S>, S> finisher() {
        return ArithmeticAccumulator::result;
    }

    /**
     * Performs characteristics.
     * @return the result of characteristics
     */
    @Override
    /**
     * Performs characteristics.
     * @return the result of characteristics
     */
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}
