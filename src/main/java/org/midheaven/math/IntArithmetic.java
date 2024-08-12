package org.midheaven.math;

import java.util.function.BiFunction;

final class IntArithmetic implements Arithmetic<Int, Rational> {

    static final IntArithmetic INSTANCE = new IntArithmetic();

    @Override
    public Int zero() {
        return Int.ZERO;
    }

    @Override
    public Int sum(Int a, Int b) {
        return a.plus(b);
    }

    @Override
    public Rational over(Int a, long b) {
        return a.over(b);
    }

    @Override
    public ArithmeticAccumulatorCollector<Int, Int> sumCollector(){
        return new ArithmeticAccumulatorCollector<>(new IntSumArithmeticAccumulator<>((total, count) -> total));
    }

    @Override
    public ArithmeticAccumulatorCollector<Int, Rational> averageCollector(){
        return new ArithmeticAccumulatorCollector<>(new IntSumArithmeticAccumulator<>(Int::over));
    }
}

final class IntSumArithmeticAccumulator<R> implements ArithmeticAccumulator<Int, R>{

    final IntAccumulator accumulator = new IntAccumulator();

    private final BiFunction<Int, Long, R> finisher;
    IntAccumulator numerator = new IntAccumulator();
    IntAccumulator denominator = new IntAccumulator(Int.ONE);
    long count;

    IntSumArithmeticAccumulator(BiFunction<Int, Long, R> finisher){
        this.finisher = finisher;
    }

    @Override
    public ArithmeticAccumulator<Int, R> newInstance() {
        return new IntSumArithmeticAccumulator<>(finisher);
    }

    @Override
    public void accumulate(Int value) {
        count++;
        accumulator.incrementBy(value);
    }

    @Override
    public ArithmeticAccumulator<Int, R> combine(ArithmeticAccumulator<Int, R> other) {
         if (other instanceof IntSumArithmeticAccumulator a){
             var c = new IntSumArithmeticAccumulator(finisher);
             c.accumulator.incrementBy(this.accumulator);
             c.accumulator.incrementBy(a.accumulator);
         }
         throw new ClassCastException();
    }

    @Override
    public R result() {
        return finisher.apply(accumulator.get(), count);
    }
}