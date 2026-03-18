package org.midheaven.math;

import org.midheaven.lang.HashCode;
import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;

@ValueClass
final class InvertedRational implements Rational {
    
    private final long denominator;
    
    InvertedRational(long denominator){
        this.denominator = denominator;
    }
    
    @Override
    public boolean equals(Object other) {
        return other instanceof InvertedRational inv
            && this.denominator == inv.denominator;
    }
    
    @Override
    public int hashCode() {
        return HashCode.asymmetric().add(1).add(denominator).hashCode();
    }
    
    @Override
    public String toString() {
        return denominator <0 ? "-1/" + (-denominator) : "1/" + denominator;
    }
    
    @Override
    public boolean isNegativeOne() {
        return denominator <0;
    }
    
    
    @Override
    public Int numerator() {
        return denominator < 0 ? Int.NEGATIVE_ONE : Int.ONE;
    }
    
    
    @Override
    public Int denominator() {
        return denominator < 0 ? Int.of(-denominator) : Int.of(denominator);
    }
    
    
    @Override
    public Rational square() {
        try {
            return new InvertedRational(Math.multiplyExact(this.denominator, this.denominator));
        } catch (ArithmeticException e){
            return DynamicRational.of(Int.ONE, Int.of(denominator)).square();
        }
    }
    
    
    @Override
    public Rational cube() {
        try {
            return new InvertedRational(Math.multiplyExact(Math.multiplyExact(this.denominator, this.denominator), this.denominator));
        } catch (ArithmeticException e){
            return DynamicRational.of(Int.ONE, Int.of(denominator)).cube();
        }
    }
    
    
    @Override
    public BigDecimal toBigDecimal() {
        return RationalDivisionSpecification.reduceToBigDecimal(this);
    }
    
    @Override
    public long toLong() {
        return 0;
    }
    
    
    @Override
    public Rational floor() {
        return Rational.ZERO;
    }
    
    
    @Override
    public Rational ceil() {
        return denominator < 0 ? Rational.NEGATIVE_ONE : Rational.ONE;
    }
    
    @Override
    public boolean isWhole() {
        return false;
    }
    
    
    @Override
    public Rational increment() {
        // 1 /a + 1 = 1 + a / a
        try {
            return Rational.of(Math.incrementExact(this.denominator) , this.denominator);
        } catch (ArithmeticException e){
            return DynamicRational.from(this).increment();
        }

    }
    
    
    @Override
    public Rational decrement() {
        try {
            return Rational.of(Math.decrementExact(this.denominator) , this.denominator);
        } catch (ArithmeticException e){
            return DynamicRational.from(this).decrement();
        }
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
        return Long.signum(denominator);
    }
    
    @Override
    public Rational negate() {
        return new InvertedRational(-denominator);
    }
    
    @Override
    public boolean isZero() {
        return false;
    }
    
    @Override
    public boolean isPositive() {
        return denominator > 0;
    }
    
    @Override
    public boolean isNegative() {
        return denominator < 0;
    }
    
    @Override
    public Rational plus( Rational other) {
        // 1 /a + b/c = (c + b) / ac
        return other.numerator().plus(other.denominator()).over(other.denominator().times(denominator));
    }
    
    @Override
    public Rational invert() {
        return new WholeRational(denominator);
    }
    
    @Override
    public boolean isOne() {
        return false;
    }
    
    
    @Override
    public Rational times( Rational other) {
        return other.numerator().over(other.denominator().times(denominator));
    }
    
}
