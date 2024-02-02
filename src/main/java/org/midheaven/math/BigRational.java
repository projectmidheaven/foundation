package org.midheaven.math;

import org.midheaven.lang.HashCode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/*value*/ class BigRational implements Rational {

    static BigRational from(Rational other) {
        return new BigRational(other.numerator(), other.denominator());
    }

    static BigRational of (BigInteger numerator, BigInteger denominator){
        if (denominator.signum() == 0){
            throw new ArithmeticException("Denominator cannot be zero");
        } else if (denominator.signum()  < 0) {
            // switch signs
            numerator = numerator.negate();
            denominator = denominator.negate();
        }

        if (denominator.equals(BigInteger.ONE)){
            // gcd division is not necessary
            return new BigRational(numerator, denominator);
        }

        var gcd = numerator.gcd(denominator);
        numerator = numerator.divide(gcd);
        denominator  = denominator.divide(gcd);

        return new BigRational(numerator, denominator);
    }

    private final BigInteger numerator;
    private final BigInteger denominator;

    public BigRational(BigInteger numerator, BigInteger denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public boolean isZero() {
        return this.numerator.signum() == 0;
    }

    @Override
    public boolean isOne() {
        return this.numerator.compareTo(this.denominator) == 0;
    }

    @Override
    public boolean isNegativeOne() {
        return this.numerator.negate().compareTo(this.denominator) == 0;
    }

    @Override
    public Rational plus(Rational other) {
        if (other.isZero()){
            return this;
        } else if (this.isZero()){
            return other;
        }
        // a/b + c/d = (ad + bc) / bd
        return of(
            this.numerator.multiply(other.denominator()).add(other.numerator().multiply(this.denominator)),
            this.denominator.multiply(other.denominator())
        );
    }

    @Override
    public Rational times(Rational other) {
        if (this.isZero() || other.isZero()){
            return Rational.ZERO;
        } else if (other.isOne()){
            return this;
        } else if (this.isOne()){
            return other;
        }
        // a/b * c/d = ac / bd
        return of(
            this.numerator.multiply(other.numerator()),
            this.denominator.multiply(other.denominator())
        );
    }

    @Override
    public Rational negate() {
        return new BigRational(this.numerator.negate(), this.denominator);
    }

    @Override
    public Rational invert() {
        if (this.numerator.signum() == 0){
            throw new ArithmeticException("Cannot invert zero");
        }else if (this.numerator.signum() > 0){
            return new BigRational(this.denominator, this.numerator);
        }
        return new BigRational(this.denominator.negate(), this.numerator.negate());
    }

    @Override
    public int sign() {
        return numerator.signum();
    }

    @Override
    public BigInteger numerator() {
        return this.numerator;
    }

    @Override
    public BigInteger denominator() {
        return this.denominator;
    }

    @Override
    public Rational square() {
        return new BigRational(this.numerator.pow(2), this.numerator.pow(2));
    }

    @Override
    public BigDecimal toBigDecimal() {
        return new BigDecimal(this.numerator).divide(new BigDecimal(this.denominator), 20, RoundingMode.HALF_DOWN);
    }

    @Override
    public long toLong() {
        return toBigDecimal().longValue();
    }

    @Override
    public Rational floor() {
        return null;
    }

    @Override
    public Rational ceil() {
        return null;
    }

    @Override
    public boolean isWhole() {
        return this.denominator.equals(BigInteger.ONE);
    }

    @Override
    public Rational raisedTo(int exponent) {
        if (exponent == 0){
            return Rational.ONE;
        } else if (exponent == 1){
            return this;
        }
        return of(this.numerator.pow(exponent), this.denominator.pow(exponent)).reduce();
    }

    private Rational reduce() {
        if (numerator.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) < 0
            && numerator.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) > 0
            && denominator.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) < 0
            && denominator.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) > 0) {
            return LongRational.of(this.numerator.longValue(), this.denominator.longValue());
        }
        return this;
    }

    @Override
    public int compareTo(Rational other) {
        return this.numerator.multiply(other.denominator()).compareTo(this.denominator.multiply(other.numerator()));
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof Rational rational){
            return this.numerator().equals(rational.numerator())
                && this.denominator().equals(rational.denominator());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return HashCode.asymmetric().add(this.numerator).add(this.numerator).hashCode();
    }

    @Override
    public String toString(){
        if (denominator.equals(BigInteger.ONE)){
            return numerator.toString();
        }
        return numerator + "/" + denominator;
    }
}
