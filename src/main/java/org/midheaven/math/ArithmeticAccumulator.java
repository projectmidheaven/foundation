package org.midheaven.math;

public interface ArithmeticAccumulator<T, R> {

    ArithmeticAccumulator<T, R> newInstance();

    void accumulate(T value);

    ArithmeticAccumulator<T, R> combine(ArithmeticAccumulator<T, R> other);

    R result();

}
