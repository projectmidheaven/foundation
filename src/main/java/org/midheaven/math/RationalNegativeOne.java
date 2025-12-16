package org.midheaven.math;

import org.midheaven.lang.Nullable;
import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;

@ValueClass
final class RationalNegativeOne implements Rational{
    
    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof Rational that && that.isNegativeOne();
    }
    
    @Override
    public int hashCode() {
        return -1;
    }
    
    @Override
    public String toString() {
        return "-1";
    }
    
    
    @Override
    public boolean isNegativeOne() {
        return true;
    }
    
    @Nullable
    @Override
    public Int numerator() {
        return Int.NEGATIVE_ONE;
    }
    
    @Nullable
    @Override
    public Int denominator() {
        return Int.ONE;
    }
    
    @Nullable
    @Override
    public Rational square() {
        return Rational.ONE;
    }
    
    @Nullable
    @Override
    public Rational cube() {
        return this;
    }
    
    @Nullable
    @Override
    public BigDecimal toBigDecimal() {
        return BigDecimal.ONE.negate();
    }
    
    @Override
    public long toLong() {
        return -1L;
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
    
    @Nullable
    @Override
    public Rational increment() {
        return Rational.ZERO;
    }
    
    @Nullable
    @Override
    public Rational decrement() {
        return new WholeRational(2);
    }
    
    @Override
    public int compareTo(long other) {
        return Long.compare(-1, other);
    }
    
    @Override
    public int compareTo(Rational o) {
        return -o.denominator().compareTo(o.numerator());
    }
    
    @Override
    public int sign() {
        return -1;
    }
    
    @Override
    public Rational negate() {
        return Rational.ONE;
    }
    
    @Override
    public boolean isZero() {
        return false;
    }
    
    @Nullable
    @Override
    public Rational plus(@Nullable Rational other) {
        return other.decrement();
    }
    
    @Override
    public Rational invert() {
        return this;
    }
    
    @Override
    public boolean isOne() {
        return false;
    }
    
    @Nullable
    @Override
    public Rational times(@Nullable Rational other) {
        return other.negate();
    }
}
