package org.midheaven.math;

import org.midheaven.lang.Check;
import org.midheaven.lang.LongOrdered;
import org.midheaven.lang.NotNullable;
import org.midheaven.lang.Nullable;
import org.midheaven.lang.Ordered;
import org.midheaven.lang.Signed;
import org.midheaven.lang.Strings;
import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

@ValueClass
public sealed interface Rational extends Ordered<Rational>, LongOrdered, Signed<Rational>, Field<Rational>
    permits DynamicRational, InvertedRational, RationalNegativeOne, RationalOne, RationalZero, WholeRational {
    
    static @NotNullable Arithmetic<Rational, Rational> arithmetic(){
        return RationalArithmetic.INSTANCE;
    }

    @NotNullable Rational ZERO = new RationalZero();
    @NotNullable Rational ONE = new RationalOne();
    @NotNullable Rational NEGATIVE_ONE = new RationalNegativeOne();
    @NotNullable Rational TEN = new WholeRational(10);
    @NotNullable Rational PI = of(3141592653589793238L, 1000000000000000000L);
    
    static @NotNullable Rational of(long numerator, long denominator){
        if (numerator == 0){
            return ZERO;
        } else if (numerator == denominator){
            return ONE;
        } else if (numerator == -denominator){
            return NEGATIVE_ONE;
        } else if (denominator == 1){
            // whole number
            return of(numerator);
        } else if (denominator < 0){
            // normalize the negative sign on the numerator
            return of(-numerator, - denominator);
        }  else if (numerator == 1){
            return new InvertedRational(denominator);
        }
        return DynamicRational.of(Int.of(numerator), Int.of(denominator)).reduce();
    }
    
    static @NotNullable Rational of(long numerator){
        if (numerator == 0){
            return ZERO;
        } else if (numerator == 1){
            return ONE;
        }  else if (numerator == -1){
            return NEGATIVE_ONE;
        }
        return new WholeRational(numerator);
    }
    
    static @Nullable Rational of(@Nullable BigInteger numerator, @Nullable BigInteger denominator){
        if (numerator == null || denominator == null){
            return null;
        }
        
        return DynamicRational.of(
            Objects.requireNonNull(Int.of(numerator)),
            Objects.requireNonNull(Int.of(denominator))
        ).reduce();
    }
    
    static @Nullable Rational of(@Nullable BigInteger numerator){
        if (numerator == null){
            return null;
        } else if (numerator.signum() == 0){
            return ZERO;
        } else if (BigInteger.ONE.equals(numerator)){
            return ONE;
        } else if (BigInteger.ONE.negate().equals(numerator)){
            return NEGATIVE_ONE;
        }
        return DynamicRational.of(Objects.requireNonNull(Int.of(numerator)), Int.ONE);
    }
    
    static @NotNullable Rational of(@NotNullable BigDecimal decimal){
        Check.argumentIsNotNull(decimal);
        if (decimal.signum() == 0){
            return ZERO;
        } else if (BigDecimal.ONE.equals(decimal)){
            return ONE;
        } else if (BigDecimal.ONE.negate().equals(decimal)){
            return NEGATIVE_ONE;
        }
        
        int scale = decimal.scale();
        if (scale <= 0) {
            // whole number
            return of(decimal.toBigInteger(), BigInteger.ONE);
        } else {
            var denominator = BigInteger.TEN.pow(scale);
            var numerator = decimal.unscaledValue();
            return of(numerator, denominator);
        }
    }
    
    static @Nullable Rational parse(@Nullable String number){
        if (number == null || number.isBlank()){
            return null;
        }
        if (number.contains("/")){
            try {
                var numbers = Strings.Splitter.split( number).by("/").sequence()
                        .map(String::trim)
                        .map(BigInteger::new);
                return numbers.getAt(0).zip(numbers.getAt(1), Rational::of).orElseThrow();
            }catch (Exception e){
                throw new IllegalArgumentException("'" + number + "' is not a parseable Rational value");
            }

        }
        return of(new BigDecimal(number));
    }
    
    static @Nullable Rational from(@Nullable Double value){
        if (value == null || value.isInfinite() || value.isNaN()){
            return null;
        }
        return parse(value.toString());
    }
    
    @Override
    boolean equals(Object other);
    
    @Override
    int hashCode();
    
    default @NotNullable Rational plus(long other) {
        return this.plus(Rational.of(other));
    }
    
    default @NotNullable Rational minus(@NotNullable Rational other) {
        Check.argumentIsNotNull(other, "other");
        // a - b = -b + a
        return other.negate().plus(this);
    }
    
    default @NotNullable Rational minus(long other) {
        return this.plus(Rational.of(-other));
    }
    
    default @NotNullable Rational times(long other) {
        return this.times(Rational.of(other));
    }
    
    default @NotNullable Rational over(@NotNullable Rational other) {
        Check.argumentIsNotNull(other, "other");
        if (other.isZero()){
            throw ArithmeticExceptions.divisionByZero();
        }
        return this.times(other.invert());
    }
    
    default @NotNullable Rational over(long other) {
        if (other == 0){
            throw ArithmeticExceptions.divisionByZero();
        }
        return this.over(Rational.of(other));
    }
    
    default @NotNullable Rational abs(){
        return this.sign() < 0 ? this.negate() : this;
    }
    
    @NotNullable Int numerator();
    @NotNullable Int denominator();
    
    @NotNullable Rational square();
    
    @NotNullable Rational cube();
    
    @NotNullable BigDecimal toBigDecimal();

    /***
     * The equivalent {@code Long} value.
     * This is equivalent to divide the numerator by the denominator using long division, discarding the remainder.
     * If {@link #isWhole()}  is {@code true}, and the value fits in a Long, this value is the numerator.
     * @return the equivalent long value.
     */
    long toLong();
    
    @NotNullable Rational floor();
    
    @NotNullable Rational ceil();
    
    boolean isWhole();
    
    default @NotNullable Rational raisedTo(int exponent) {
        if (exponent == 0){
            return Rational.ONE; // 0^0 = 1 per definition
        } else if (exponent == 1){
            return this;
        } else if (exponent == 2){
            return this.square();
        } else if (exponent == 3){
            return this.cube();
        } else if (exponent < 0){
            return this.raisedTo(-exponent).invert();
        }
        return DynamicRational.of(numerator().raisedTo(exponent), denominator().raisedTo(exponent)).reduce();
    }
    
    default boolean isNegativeOne() {
        return numerator().isNegativeOne() && denominator().isOne();
    }
    
    default boolean isOne() {
        return numerator().isOne() && denominator().isOne();
    }
    
    @Override
    boolean isZero();
    
    @NotNullable Rational increment();
    @NotNullable Rational decrement();
}
