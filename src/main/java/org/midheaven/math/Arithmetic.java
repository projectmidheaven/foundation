package org.midheaven.math;

import java.util.function.BiFunction;

public interface Arithmetic<T, S> {

    static <V,R> Arithmetic<V, R> of(V zero, BiFunction<V,V,V> sum, BiFunction<V,Long,R> scale){
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
                return scale.apply(a, b);
            }
        };
    }

    T zero();

    T sum(T a, T b);

    S over(T a, long b);
}
