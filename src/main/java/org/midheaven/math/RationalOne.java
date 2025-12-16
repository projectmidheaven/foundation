package org.midheaven.math;

import org.midheaven.lang.Nullable;
import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;

@ValueClass
final class RationalOne implements Rational{
    
    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof Rational that && that.isOne();
    }
    
    @Override
    public int hashCode() {
        return 1;
    }
    
    @Override
    public String toString() {
        return "1";
    }
    
    
    
    @Override
    public boolean isNegativeOne() {
        return false;
    }
    
    @Nullable
    @Override
    public Int numerator() {
        return Int.ONE;
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
        return BigDecimal.ONE;
    }
    
    @Override
    public long toLong() {
        return 1;
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
        return new WholeRational(2);
    }
    
    @Nullable
    @Override
    public Rational decrement() {
        return Rational.ZERO;
    }
    
    @Override
    public int compareTo(long other) {
        return Long.compare(1, other);
    }
    
    @Override
    public int compareTo(Rational o) {
        return o.denominator().compareTo(o.numerator());
    }
    
    @Override
    public int sign() {
        return 1;
    }
    
    @Override
    public Rational negate() {
        return Rational.NEGATIVE_ONE;
    }
    
    @Override
    public boolean isZero() {
        return false;
    }
    
    @Nullable
    @Override
    public Rational plus(@Nullable Rational other) {
        return other.increment();
    }
    
    @Override
    public Rational invert() {
        return this;
    }
    
    @Override
    public boolean isOne() {
        return true;
    }
    
    @Nullable
    @Override
    public Rational times(@Nullable Rational other) {
        return other;
    }
}
