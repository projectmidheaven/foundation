package org.midheaven.math;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Generator for Random values.
 * @param <T> the type of element being generated
 */
public interface RandomGenerator<T>  {

    /**
     * The next generated value
     * @return the next generated value
     */
    T next();

    /**
     * Returns a {@link Stream } of random generated values
     * @return a {@link Stream } of random generated values
     */
    default Stream<T> stream(){
        return Stream.generate(this::next);
    }

    /**
     * Returns a new {@link RandomGenerator } based on the values of this one.
     * @return  a new {@link RandomGenerator } based on the values of this one.
     */
    default <R> RandomGenerator<R> generateNext(Function<RandomGenerator<T>, R> generator) {
        return () -> generator.apply(this);
    }
}
