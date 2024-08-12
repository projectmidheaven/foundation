package org.midheaven.math;

import org.midheaven.lang.LongOrdered;
import org.midheaven.lang.NotNull;
import org.midheaven.lang.Ordered;
import org.midheaven.lang.Signed;
import org.midheaven.lang.Strings;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public interface Rational extends Ordered<Rational>, LongOrdered, Signed<Rational>, Field<Rational>{


    static @NotNull Arithmetic<Rational, Rational> arithmetic(){
        return RationalArithmetic.INSTANCE;
    }

    @NotNull Rational ZERO = new WholeRational(0);
    @NotNull Rational ONE = new WholeRational(1);
    @NotNull Rational MINUS_ONE = new WholeRational(-1);
    @NotNull Rational TEN = new WholeRational(10L);

    static Rational of(long numerator, long denominator){
        if (denominator == 1){
            return new WholeRational(numerator);
        } else if (denominator == -1){
            return new WholeRational(-numerator);
        }
        return IntRational.of(Int.of(numerator), Int.of(denominator)).reduce();
    }

    static Rational of(long numerator){
        return new WholeRational(numerator);
    }

    static Rational of(BigInteger numerator, BigInteger denominator){
        if (numerator == null){
            return null;
        }
        Objects.requireNonNull(denominator);

        return IntRational.of(Int.of(numerator), Int.of(denominator)).reduce();
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
            return of(decimal.toBigInteger(), BigInteger.ONE);
        } else {
            var denominator = BigInteger.TEN.pow(scale);
            var numerator = decimal.unscaledValue();
            return of(numerator, denominator);
        }
    }

    static Rational parse(String number){
        if (number == null || number.isBlank()){
            return null;
        }
        if (number.contains("/")){
            try {
                var numbers = Strings.Splitter.split( number).by("/").sequence()
                        .map(it -> it.trim())
                        .map(BigInteger::new);
                return numbers.getAt(0).zip(numbers.getAt(1), Rational::of).orElseThrow();
            }catch (Exception e){
                throw new IllegalArgumentException("'" + number + "' is not a parseable Rational value");
            }

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

    default Rational plus(long other) {
        return this.plus(Rational.of(other));
    }

    default Rational minus(Rational other) {
        Objects.requireNonNull(other);
        return this.plus(other.negate());
    }

    default Rational minus(long other) {
        return this.plus(Rational.of(-other));
    }

    default Rational times(long other) {
        return this.times(Rational.of(other));
    }

    default Rational over(Rational other) {
        Objects.requireNonNull(other);
        return this.times(other.invert());
    }

    default Rational over(long other) {
        return this.over(Rational.of(other));
    }

    @NotNull default Rational abs(){
        return this.sign() < 0 ? this.negate() : this;
    }

    @NotNull Int numerator();
    @NotNull Int denominator();

    @NotNull Rational square();

    @NotNull Rational cube();

    @NotNull BigDecimal toBigDecimal();

    /***
     * The equivalent {@code Long} value.
     * This is equivalent to divide the numerator by the denominator using long division, discarding the remainder.
     * If {@link #isWhole()}  is {@code true}, and the value fits in a Long, this value is the numerator.
     * @return the equivalent long value.
     */
    long toLong();

    @NotNull Rational floor();

    @NotNull Rational ceil();

    boolean isWhole();

    @NotNull Rational raisedTo(int exponent);


}
