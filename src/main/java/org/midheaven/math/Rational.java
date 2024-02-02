package org.midheaven.math;

import org.midheaven.lang.Ordered;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public interface Rational extends Ordered<Rational>, Field<Rational>{


    static Arithmetic<Rational, Rational> arithmetic(){
        return Arithmetic.of( Rational.ZERO , Rational::plus, Rational::over);
    }

    Rational ZERO = new LongRational(0,1);
    Rational ONE = new LongRational(1,1);
    Rational MINUS_ONE = new LongRational(-1,1);
    Rational TEN = new LongRational(10L, 1L);

    static Rational of(long numerator, long denominator){
        return LongRational.of(numerator, denominator);
    }

    static Rational of(long numerator){
        return of(numerator, 1);
    }

    static Rational of(BigInteger numerator, BigInteger denominator){
        if (numerator == null){
            return null;
        }
        Objects.requireNonNull(denominator);

        return BigRational.of(numerator, denominator);
    }

    static Rational of(BigInteger numerator){
        if (numerator == null){
            return null;
        }
        return of(numerator, BigInteger.ONE);
    }

    static Rational of(BigDecimal decimal){
        if (decimal == null){
            return null;
        }
        int scale = decimal.scale();
        if (scale <= 0) {
            return new BigRational(decimal.toBigInteger(), BigInteger.ONE);
        } else {
            var denominator = BigInteger.TEN.pow(scale);
            var numerator = decimal.unscaledValue();
            return BigRational.of(numerator, denominator);
        }
    }

    static Rational parse(String number){
        if (number == null || number.isBlank()){
            return null;
        }
        return of(new BigDecimal(number));
    }

    static Rational from(Double value){
        if (value == null || value.isInfinite() || value.isNaN()){
            return null;
        }
        return parse(value.toString());
    }

    boolean isNegativeOne();

    default boolean isPositive(){
        return sign() > 0;
    }

    default boolean isNegative(){
        return sign() < 0;
    }

    default Rational abs() {
        return this.sign() < 0 ? this.negate() : this;
    }

    default Rational plus(long other) {
        return this.plus(Rational.of(other));
    }

    default Rational minus(Rational other) {
        return this.plus(other.negate());
    }

    default Rational minus(long other) {
        return this.plus(Rational.of(-other));
    }

    default Rational times(long other) {
        return this.times(Rational.of(other));
    }

    default Rational over(Rational other) {
        return this.times(other.invert());
    }

    default Rational over(long other) {
        return this.over(Rational.of(other));
    }

    int sign();

    BigInteger numerator();
    BigInteger denominator();

    Rational square();

    BigDecimal toBigDecimal();

    /***
     * The equivalent {@code Long} value.
     * This is equivalent to divide the numerator by the denominator using long division, discarding the remainder.
     * If {@link #isWhole()}  is {@code true}, and the value fits in a Long, this value is the numerator.
     * @return the equivalent long value.
     */
    long toLong();

    Rational floor();

    Rational ceil();

    boolean isWhole();

    Rational raisedTo(int exponent);
}
