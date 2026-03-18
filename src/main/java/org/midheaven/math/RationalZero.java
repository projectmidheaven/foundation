package org.midheaven.math;

import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;

@ValueClass
final class RationalZero implements Rational{
    
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof Rational that && that.isZero());
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
    
    
    @Override
    public Int numerator() {
        return Int.ZERO;
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
        return BigDecimal.ZERO;
    }
    
    @Override
    public long toLong() {
        return 0;
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
    
    @Override
    public boolean isPositive() {
        return false;
    }
    
    @Override
    public boolean isNegative() {
        return false;
    }
    
    
    @Override
    public Rational plus( Rational other) {
        return other;
    }
    
    
    @Override
    public Rational minus( Rational other) {
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
    
    
    @Override
    public Rational times( Rational other) {
        return this;
    }
    
    
    @Override
    public Rational raisedTo(int exponent) {
        if (exponent == 0){
            return Rational.ONE; // 0^0 = 1 per definition
        }
        return this;
    }
    
    
    @Override
    public Rational increment() {
        return Rational.ONE;
    }
    
    
    @Override
    public Rational decrement() {
        return Rational.NEGATIVE_ONE;
    }
}
