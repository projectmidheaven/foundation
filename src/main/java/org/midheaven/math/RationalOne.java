package org.midheaven.math;

import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;

@ValueClass
/**
 * Represents Rational One.
 */
final class RationalOne implements Rational{
    
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof Rational that && that.isOne());
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
    
    
    @Override
    public Int numerator() {
        return Int.ONE;
    }
    
    
    @Override
    public Int denominator() {
        return Int.ONE;
    }
    
    
    @Override
    public Rational square() {
        return this;
    }
    
    
    @Override
    public Rational cube() {
        return this;
    }
    
    
    @Override
    public BigDecimal toBigDecimal() {
        return BigDecimal.ONE;
    }
    
    @Override
    public long toLong() {
        return 1;
    }
    
    
    @Override
    public Rational floor() {
        return this;
    }
    
    
    @Override
    public Rational ceil() {
        return this;
    }
    
    @Override
    public boolean isWhole() {
        return true;
    }
    
    
    @Override
    public Rational increment() {
        return new WholeRational(2);
    }
    
    
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
    
    @Override
    public boolean isPositive() {
        return true;
    }
    
    @Override
    public boolean isNegative() {
        return false;
    }
    
    @Override
    public Rational plus( Rational other) {
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
    
    
    @Override
    public Rational times( Rational other) {
        return other;
    }
}
