package org.midheaven.math;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Stream;

public interface RandomGenerator<T>  {

    static RandomGeneratorBuilder from(Random random){
        return new RandomGeneratorBuilder(() ->random);
    }

    static RandomGeneratorBuilder standard(){
        return new RandomGeneratorBuilder(ThreadLocalRandom::current);
    }

    static RandomGeneratorBuilder secure(){
        return from(new SecureRandom());
    }

    static RandomGeneratorBuilder seedable(long seed){
        return from(new Random(seed));
    }

    T next();

    default Stream<T> stream(){
        return Stream.generate(this::next);
    }

    default <R> RandomGenerator<R> generateNext(Function<RandomGenerator<T>, R> generator) {
        return () -> generator.apply(this);
    }
}
