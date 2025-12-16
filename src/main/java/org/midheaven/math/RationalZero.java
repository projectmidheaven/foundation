package org.midheaven.math;

import org.midheaven.lang.Nullable;
import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;

@ValueClass
final class RationalZero implements Rational{
    
    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof Rational that && that.isZero();
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    @Override
    public String toString() {
        return "0";
    }
    
    
    @Override
    public boolean isNegativeOne() {
        return false;
    }
    
    @Nullable
    @Override
    public Int numerator() {
        return Int.ZERO;
    }
    
    @Nullable
    @Override
    public Int denominator() {
        return Int.ONE;
    }
    
    @Nullable
    @Override
    public Rational square() {
        return this;
    }
    
    @Nullable
    @Override
    public Rational cube() {
        return this;
    }
    
    @Nullable
    @Override
    public BigDecimal toBigDecimal() {
        return BigDecimal.ZERO;
    }
    
    @Override
    public long toLong() {
        return 0;
    }
    
    @Nullable
    @Override
    public Rational floor() {
        return this;
    }
    
    @Nullable
    @Override
    public Rational ceil() {
        return this;
    }
    
    @Override
    public boolean isWhole() {
        return true;
    }
    
    @Override
    public int compareTo(long other) {
        return Long.compare(0, other);
    }
    
    @Override
    public int compareTo(Rational o) {
        return -o.sign();
    }
    
    @Override
    public int sign() {
        return 0;
    }
    
    @Override
    public Rational negate() {
        return this;
    }
    
    @Override
    public boolean isZero() {
        return true;
    }
    
    @Nullable
    @Override
    public Rational plus(@Nullable Rational other) {
        return other;
    }
    
    @Nullable
    @Override
    public Rational minus(@Nullable Rational other) {
        return other.negate();
    }
    
    @Override
    public Rational invert() {
        throw ArithmeticExceptions.divisionByZero();
    }
    
    @Override
    public boolean isOne() {
        return false;
    }
    
    @Nullable
    @Override
    public Rational times(@Nullable Rational other) {
        return this;
    }
    
    @Nullable
    @Override
    public Rational raisedTo(int exponent) {
        if (exponent == 0){
            return Rational.ONE; // 0^0 = 1 per definition
        }
        return this;
    }
    
    @Nullable
    @Override
    public Rational increment() {
        return Rational.ONE;
    }
    
    @Nullable
    @Override
    public Rational decrement() {
        return Rational.NEGATIVE_ONE;
    }
}
