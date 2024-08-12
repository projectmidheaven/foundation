package org.midheaven.math;

public class Numbers {

    public interface Floats {

        static Arithmetic<Float, Float> arithmetic(){
            return Arithmetic.of(0f, Float::sum, (x,y) -> x / y );
        }
    }

    public interface Doubles {
        static Arithmetic<Double, Double> arithmetic(){
            return Arithmetic.of(0d, Double::sum, (x,y) -> x / y );
        }
    }

    public interface Integers {

        static Arithmetic<Integer, Rational> arithmetic(){
            return Arithmetic.of(0, Integer::sum, Rational::of);
        }

    }

    public interface Longs {

        static Arithmetic<Long, Rational> arithmetic(){
            return Arithmetic.of(0L, Long::sum, Rational::of);
        }

    }

    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}
