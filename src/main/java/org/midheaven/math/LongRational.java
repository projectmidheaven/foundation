package org.midheaven.math;

import org.midheaven.lang.HashCode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/*value*/ class LongRational implements Rational {

    static LongRational of (long numerator, long denominator){
        if (denominator == 0){
            throw new ArithmeticException("Denominator cannot be zero");
        } else if (denominator <0) {
            // switch signs
            numerator = -numerator;
            denominator = -denominator;
        }

        if (denominator == 1 || numerator == 1){
            // gcd division is not necessary
            return new LongRational(numerator, denominator);
        }

        var gcd = Numbers.gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        return new LongRational(numerator, denominator);
    }

    @Override
    public boolean isZero() {
        return this.numerator == 0;
    }

    @Override
    public boolean isOne() {
        return this.numerator == this.denominator;
    }

    @Override
    public boolean isNegativeOne() {
        return -this.numerator == this.denominator;
    }

    private final long numerator;
    private final long denominator;

    LongRational(long numerator, long denominator){
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Rational plus(LongRational other) {
        if (other.numerator == 0){
            return this;
        } else if ( this.numerator == 0){
            return other;
        } else if (other.isOne()){
            // a/b + 1 = (a + b) / b
            try {
                var n= Math.addExact(this.numerator, this.denominator);
                return of(n, this.denominator);
            }catch (ArithmeticException e){
                return BigRational.from(this).plus(other);
            }
        }  else if (other.isNegativeOne()){
            // a/b - 1 = (a - b) / b
            try {
                var n= Math.subtractExact(this.numerator, this.denominator);
                return of(n, this.denominator);
            }catch (ArithmeticException e){
                return BigRational.from(this).plus(other);
            }
        }
        // a/b + c/d = (ad + bc) / bd
        try {
            var n= Math.addExact(Math.multiplyExact(this.numerator, other.denominator),Math.multiplyExact(other.numerator, this.denominator));
            var d = Math.multiplyExact(this.denominator, other.denominator);
            return of(n, d);
        }catch (ArithmeticException e){
            return BigRational.from(this).plus(other);
        }
    }



    @Override
    public Rational plus(Rational other) {

        if (other instanceof LongRational longRational){
            return this.plus(longRational);
        }

        return BigRational.from(this).plus(other);
    }

    public Rational times(LongRational other) {
        // a/b * c/d = ac / bd
        try {
            var n= Math.multiplyExact(this.numerator, other.numerator);
            var d = Math.multiplyExact(this.denominator, other.denominator);
            return of(n, d);
        }catch (ArithmeticException e){
            return BigRational.from(this).times(other);
        }
    }

    @Override
    public Rational times(Rational other) {

        if (other instanceof LongRational longRational){
            return this.times(longRational);
        }

        return BigRational.from(this).times(other);
    }

    @Override
    public Rational negate() {
        return new LongRational(-this.numerator, this.denominator);
    }

    @Override
    public Rational invert() {
        if (this.numerator == 0){
            throw new ArithmeticException("Cannot invert zero");
        }else if (this.numerator > 0){
            return new LongRational(this.denominator, this.numerator);
        }
        return new LongRational(-this.denominator, -this.numerator);
    }

    @Override
    public int sign() {
        return Long.signum(this.numerator);
    }

    @Override
    public BigInteger numerator() {
        return BigInteger.valueOf(this.numerator);
    }

    @Override
    public BigInteger denominator() {
        return BigInteger.valueOf(this.denominator);
    }

    @Override
    public Rational square() {
        try {
            var n= Math.multiplyExact(this.numerator, this.numerator);
            var d = Math.multiplyExact(this.denominator, this.denominator);
            return of(n, d);
        }catch (ArithmeticException e){
            return BigRational.from(this).square();
        }
    }

    @Override
    public BigDecimal toBigDecimal() {
        return new BigDecimal(this.numerator).divide(new BigDecimal(this.denominator), 20, RoundingMode.HALF_EVEN);
    }


    @Override
    public long toLong() {
        return this.numerator / this.denominator;
    }

    @Override
    public Rational floor() {
        return new LongRational(this.numerator / this.denominator, 1);
    }

    @Override
    public Rational ceil() {
        // TODO
        return null;
    }

    @Override
    public boolean isWhole() {
        return this.denominator == 1;
    }

    @Override
    public Rational raisedTo(int exponent) {
        if (exponent == 0){
            return Rational.ONE;
        } else if (exponent == 1){
            return this;
        } else if (exponent == 2){
            return this.times(this);
        }  else if (exponent == 3){
            return this.times(this).times(this);
        }
        return BigRational.from(this).raisedTo(exponent);
    }

    @Override
    public int compareTo(Rational other) {
        // a/b > c/d  <=> ad > cb
        if (other instanceof LongRational longRational){
            return Long.compare(
                    Math.multiplyExact(this.numerator, longRational.denominator),
                    Math.multiplyExact(this.denominator, longRational.numerator)
            );
        }
        return this.minus(other).sign();
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof LongRational longRational){
            return this.numerator == longRational.numerator
                && this.denominator == longRational.denominator;
        } else if (other instanceof Rational rational){
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
        if (denominator == 1){
            return String.valueOf(numerator);
        }
        return numerator + "/" + denominator;
    }
}
