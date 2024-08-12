package org.midheaven.math;

import org.midheaven.lang.HashCode;
import org.midheaven.lang.NotNull;
import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;
import java.util.Objects;

@ValueClass
final class WholeRational implements Rational {

    private final long value;

    WholeRational(long numerator){ // denominator is  always 1
        this.value = numerator;
    }

    @Override
    public boolean isZero() {
        return this.value == 0;
    }

    @Override
    public boolean isOne() {
        return this.value == 1;
    }

    @Override
    public boolean isNegativeOne() {
        return this.value == -1;
    }

    @Override
    public Rational plus(Rational other) {

        if (other instanceof WholeRational wholeRational){
            try {
                var n = Math.addExact(this.value, wholeRational.value);
                return new WholeRational(n);
            }catch (ArithmeticException e){
                return IntRational.from(this).plus(other);
            }
        }

        return IntRational.from(this).plus(other);
    }

    @Override
    public Rational minus(Rational other) {
        Objects.requireNonNull(other);
        if (other instanceof WholeRational wholeRational){
            try {
                var n = Math.subtractExact(this.value, wholeRational.value);
                return new WholeRational(n);
            }catch (ArithmeticException e){
                return IntRational.from(this).minus(other);
            }
        }

        return IntRational.from(this).minus(other);
    }

    @Override
    public Rational times(Rational other) {
        Objects.requireNonNull(other);
        if (other instanceof WholeRational wholeRational){
            try {
                var n = Math.multiplyExact(this.value, wholeRational.value);
                return new WholeRational(n);
            }catch (ArithmeticException e){
                return IntRational.from(this).times(other);
            }
        }

        return IntRational.from(this).times(other);
    }

    @Override
    public Rational negate() {
        return new WholeRational(-this.value);
    }

    @Override
    public Rational invert() {
        if (this.value == 0){
            throw new ArithmeticException("Cannot invert zero");
        }else if (this.value > 0){
            return  Rational.of(1, this.value);
        }
        return Rational.of(-1, -this.value);
    }

    @Override
    public int sign() {
        return Long.signum(this.value);
    }

    @NotNull
    @Override
    public Int numerator() {
        return new Int64(value);
    }

    @NotNull
    @Override
    public Int denominator() {
        return Int.ONE;
    }

    @Override
    public Rational square() {
        try {
            var n = Math.multiplyExact(this.value, this.value);
            return new WholeRational(n);
        }catch (ArithmeticException e){
            return IntRational.from(this).square();
        }
    }

    @NotNull
    @Override
    public Rational cube() {
        try {
            var n = Math.multiplyExact(this.value, Math.multiplyExact(this.value, this.value));
            return new WholeRational(n);
        }catch (ArithmeticException e){
            return IntRational.from(this).cube();
        }
    }

    @Override
    public BigDecimal toBigDecimal() {
        return new BigDecimal(this.value);
    }

    @Override
    public long toLong() {
        return this.value;
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
        return IntRational.from(this).raisedTo(exponent);
    }

    @Override
    public int compareTo(long other) {
        return Long.compare(value, other);
    }

    @Override
    public boolean isLessThan(long factor) {
        return value < factor;
    }

    @Override
    public boolean isLessThanOrEqualTo(long factor) {
        return value <= factor;
    }

    @Override
    public boolean isGreaterThan(long factor) {
        return value <= factor;
    }

    @Override
    public boolean isGreaterThanOrEqualTo(long factor) {
        return value > factor;
    }

    @Override
    public boolean isEqualTo(long other) {
        return this.value == other;
    }

    @Override
    public int compareTo(Rational other) {
        // a/b > c/d  <=> ad > cb
        if (other instanceof WholeRational wholeRational){
            return Long.compare(this.value, wholeRational.value);
        }
        return this.minus(other).sign();
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof WholeRational wholeRational) {
            return this.value == wholeRational.value;
        } else if (other instanceof Rational rational){
            return this.numerator().equals(rational.numerator())
                    && this.denominator().equals(rational.denominator());
        }
        return false;
    }

    @Override
    public int hashCode(){
        // compatible with other implementations
        return HashCode.asymmetric().add(this.value).add(1L).hashCode();
    }

    @Override
    public String toString(){
       return String.valueOf(value);
    }
}
