package org.midheaven.math;

import org.midheaven.lang.HashCode;
import org.midheaven.lang.Nullable;
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
                return new WholeRational(Math.addExact(this.value, wholeRational.value));
            } catch (ArithmeticException e){
               // fall thought
            }
        }

        return other.plus(value);
    }
    
    @Override
    public Rational plus(long other) {
        try {
            return new WholeRational(Math.addExact(this.value, other));
        }catch (ArithmeticException e){
            return DynamicRational.from(this).plus(other);
        }
    }

    @Override
    public Rational minus(Rational other) {
        Objects.requireNonNull(other);
        
        if (other instanceof WholeRational wholeRational){
            try {
                return new WholeRational(Math.subtractExact(this.value, wholeRational.value));
            } catch (ArithmeticException e){
                // fall thought
            }
        }

        // a - b = -b + a
        return other.negate().plus(this);
    }
    
    @Override
    public Rational minus(long other) {
        try {
            return new WholeRational(Math.subtractExact(this.value, other));
        }catch (ArithmeticException e){
            return DynamicRational.from(this).minus(other);
        }
    }

    @Override
    public Rational times(Rational other) {
        Objects.requireNonNull(other);
        if (other instanceof WholeRational wholeRational){
            try {
                var n = Math.multiplyExact(this.value, wholeRational.value);
                return new WholeRational(n);
            }catch (ArithmeticException e){
                return DynamicRational.from(this).times(other);
            }
        }

        return other.times(value);
    }
    
    @Override
    public Rational times(long value) {
        try {
            var n = Math.multiplyExact(this.value, value);
            return new WholeRational(n);
        }catch (ArithmeticException e){
            return DynamicRational.from(this).times(value);
        }
    }
    
    @Override
    public Rational over(long value) {
        try {
            var n = Math.divideExact(this.value, value);
            return new WholeRational(n);
        }catch (ArithmeticException e){
            return DynamicRational.from(this).over(value);
        }
    }

    @Override
    public Rational negate() {
        return new WholeRational(-this.value);
    }

    @Override
    public Rational invert() {
        if (this.value == 0) {
            throw ArithmeticExceptions.divisionByZero();
        }
        
        return new InvertedRational(value);
    }

    @Override
    public int sign() {
        return Long.signum(this.value);
    }

    @Nullable
    @Override
    public Int numerator() {
        return Int.of(value);
    }

    @Nullable
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
            return DynamicRational.from(this).square();
        }
    }

    @Nullable
    @Override
    public Rational cube() {
        try {
            var n = Math.multiplyExact(this.value, Math.multiplyExact(this.value, this.value));
            return new WholeRational(n);
        }catch (ArithmeticException e){
            return DynamicRational.from(this).cube();
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
    
    @Nullable
    @Override
    public Rational increment() {
        try {
            return new WholeRational(Math.incrementExact(this.value));
        } catch (ArithmeticException e){
            return DynamicRational.from(this).increment();
        }
    }
    
    @Nullable
    @Override
    public Rational decrement() {
        try {
            return new WholeRational(Math.decrementExact(this.value));
        } catch (ArithmeticException e){
            return DynamicRational.from(this).decrement();
        }
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
