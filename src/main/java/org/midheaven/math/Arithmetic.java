package org.midheaven.math;

import java.util.function.BiFunction;

public interface Arithmetic<T, S> {

    static <V,R> Arithmetic<V, R> of(V zero, BiFunction<V,V,V> sum, BiFunction<V, Long, R> over){
        return new Arithmetic<V, R>() {
            @Override
            public V zero() {
                return zero;
            }

            @Override
            public V sum(V a, V b) {
                return sum.apply(a, b);
            }

            @Override
            public R over(V a, long b) {
                return over.apply(a,b);
            }

        };
    }

    T zero();

    T sum(T a, T b);

    S over(T a, long b);

    default ArithmeticAccumulatorCollector<T, T> sumCollector(){
        return new ArithmeticAccumulatorCollector<>(new SumArithmeticAccumulator<T>(this));
    }

    default ArithmeticAccumulatorCollector<T, S> averageCollector(){
        return new ArithmeticAccumulatorCollector<>(new AvgArithmeticAccumulator<T,S>(this));
    }
}

class SumArithmeticAccumulator<T> implements ArithmeticAccumulator<T,T>{

    private final Arithmetic<T, ?> arithmetic;
    private T total;

    public <S> SumArithmeticAccumulator(Arithmetic<T, S> arithmetic) {
        this.arithmetic = arithmetic;
        this.total = arithmetic.zero();
    }

    @Override
    public ArithmeticAccumulator<T, T> newInstance() {
        return new SumArithmeticAccumulator<>(arithmetic);
    }

    @Override
    public void accumulate(T value) {
        this.total = arithmetic.sum(this.total, value);
    }

    @Override
    public ArithmeticAccumulator<T, T> combine(ArithmeticAccumulator<T, T> other) {
        if (other instanceof SumArithmeticAccumulator<T> summation){
            var c = new SumArithmeticAccumulator<>(arithmetic);
            c.total = arithmetic.sum(this.total, summation.total);
            return c;
        }
        throw new ClassCastException();
    }

    @Override
    public T result() {
        return total;
    }
}

class AvgArithmeticAccumulator<T, S> implements ArithmeticAccumulator<T,S>{

    private final Arithmetic<T, S> arithmetic;
    private T total;
    private long count;

    public AvgArithmeticAccumulator(Arithmetic<T, S> arithmetic) {
        this.arithmetic = arithmetic;
        this.total = arithmetic.zero();
    }

    @Override
    public ArithmeticAccumulator<T, S> newInstance() {
        return new AvgArithmeticAccumulator<>(arithmetic);
    }

    @Override
    public void accumulate(T value) {
        this.total = arithmetic.sum(this.total, value);
        this.count++;
    }

    @Override
    public ArithmeticAccumulator<T, S> combine(ArithmeticAccumulator<T, S> other) {
        if (other instanceof AvgArithmeticAccumulator<T, S> summation){
            var c = new AvgArithmeticAccumulator<>(arithmetic);
            c.total = arithmetic.sum(this.total, summation.total);
            c.count = this.count + summation.count;
            return c;
        }
        throw new ClassCastException();
    }

    @Override
    public S result() {
        return count == 0
                ? arithmetic.over(arithmetic.zero(), 1)
                : arithmetic.over(total, count);
    }
}