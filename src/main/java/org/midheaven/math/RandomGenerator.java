package org.midheaven.math;

import java.util.function.Function;
import java.util.stream.Stream;

public interface RandomGenerator<T>  {

    /**
     * @return the next generated value
     */
    T next();

    /**
     * @return  a {@link Stream } of random generated values
     */
    default Stream<T> stream(){
        return Stream.generate(this::next);
    }

    /**
     * @return  a new {@link RandomGenerator } based on the values of this one.
     */
    default <R> RandomGenerator<R> generateNext(Function<RandomGenerator<T>, R> generator) {
        return () -> generator.apply(this);
    }
}
