package org.midheaven.math;

/**
 * Represents Numbers.
 */
public class Numbers {

    public interface Floats {

        /**
         * Performs arithmetic.
         * @return the result of arithmetic
         */
        static Arithmetic<Float, Float> arithmetic(){
            return Arithmetic.of(0f, Float::sum, (x,y) -> x / y );
        }
    }

    public interface Doubles {
        /**
         * Performs arithmetic.
         * @return the result of arithmetic
         */
        static Arithmetic<Double, Double> arithmetic(){
            return Arithmetic.of(0d, Double::sum, (x,y) -> x / y );
        }
    }

    public interface Integers {

        /**
         * Performs arithmetic.
         * @return the result of arithmetic
         */
        static Arithmetic<Integer, Rational> arithmetic(){
            return Arithmetic.of(0, Integer::sum, Rational::of);
        }

    }

    public interface Longs {

        /**
         * Performs arithmetic.
         * @return the result of arithmetic
         */
        static Arithmetic<Long, Rational> arithmetic(){
            return Arithmetic.of(0L, Long::sum, Rational::of);
        }

    }

    /**
     * Performs gcd.
     * @param a the a value
     * @param b the b value
     * @return the result of gcd
     */
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    /**
     * Performs gcd.
     * @param a the a value
     * @param b the b value
     * @return the result of gcd
     */
    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}
