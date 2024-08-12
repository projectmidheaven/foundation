package org.midheaven.math;

import java.util.function.BiFunction;

final class RationalArithmetic implements Arithmetic<Rational, Rational>{

    static final Arithmetic<Rational, Rational> INSTANCE = new RationalArithmetic();

    @Override
    public Rational zero() {
        return Rational.ZERO;
    }

    @Override
    public Rational sum(Rational a, Rational b) {
        return a.plus(b);
    }

    @Override
    public Rational over(Rational a, long b) {
        return a.over(b);
    }

    @Override
    public ArithmeticAccumulatorCollector<Rational, Rational> sumCollector(){
        return new ArithmeticAccumulatorCollector<>(new RationalSumArithmeticAccumulator((total, count) -> total));
    }

    @Override
    public ArithmeticAccumulatorCollector<Rational, Rational> averageCollector(){
        return new ArithmeticAccumulatorCollector<>(new RationalSumArithmeticAccumulator((total, count) -> total.over(count)));
    }
}

final class RationalSumArithmeticAccumulator implements ArithmeticAccumulator<Rational, Rational>{

    private final BiFunction<Rational, Long, Rational> finisher;
    IntAccumulator numerator = new IntAccumulator();
    IntAccumulator denominator = new IntAccumulator(Int.ONE);
    long count;

    RationalSumArithmeticAccumulator(BiFunction<Rational, Long, Rational> finisher){
        this.finisher = finisher;
    }

    @Override
    public ArithmeticAccumulator<Rational, Rational> newInstance() {
        return new RationalSumArithmeticAccumulator(finisher);
    }

    @Override
    public void accumulate(Rational value) {
        this.count++;
        this.numerator.timesPlusTimes(value.denominator(), this.denominator, value.numerator());
        this.denominator.incrementTimes(value.denominator());
    }

    @Override
    public ArithmeticAccumulator<Rational, Rational> combine(ArithmeticAccumulator<Rational, Rational> other) {
        if (other instanceof RationalSumArithmeticAccumulator that){
            var c = new RationalSumArithmeticAccumulator(finisher);
            c.count = this.count + that.count;
            c.numerator = this.numerator.timesPlusTimes(that.denominator, this.denominator, that.numerator);
            c.denominator = new IntAccumulator(this.denominator);
            c.denominator.incrementTimes(that.denominator);
        }
        throw new ClassCastException();
    }

    @Override
    public Rational result() {
        return finisher.apply(numerator.get().over(denominator.get()), count);
    }
}