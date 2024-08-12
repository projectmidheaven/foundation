package org.midheaven.math;

import org.midheaven.lang.NotNull;
import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

@ValueClass
public final class Int32 implements Int {

    final int value;

    Int32(int value) {
        this.value = value;
    }

    @NotNull
    @Override
    public Rational over(Int other) {
        Objects.requireNonNull(other);
        return IntRational.of(this, other);
    }

    @NotNull
    @Override
    public Rational over(long other) {
        return Rational.of(value, other);
    }

    @Override
    public boolean isNegativeOne() {
        return value == -1L;
    }

    @Override
    public int sign() {
        return Integer.signum(value);
    }

    @NotNull
    @Override
    public BigInteger toBigInteger() {
        return BigInteger.valueOf(value);
    }

    @NotNull
    @Override
    public Int square() {
        try {
            return new Int32(Math.multiplyExact(value, value));
        } catch (ArithmeticException e){
            return new Int64(value).square();
        }
    }

    @NotNull
    @Override
    public Int cube() {
        try {
            return new Int32(Math.multiplyExact(value, Math.multiplyExact(value, value)));
        } catch (ArithmeticException e){
            return new Int64(value).cube();
        }
    }

    @NotNull
    @Override
    public BigDecimal toBigDecimal() {
        return BigDecimal.valueOf(value);
    }

    @Override
    public long toLong() {
        return value;
    }

    @Override
    public int toInt() {
        return value;
    }

    @Override
    public Rational toRational() {
        return Rational.of(value);
    }

    @Override
    public Int gcd(Int other) {
        if (other instanceof Int32 that){
            return new Int32(Numbers.gcd(this.value, that.value));
        } else if (other instanceof Int64 that){
            return new Int64(Numbers.gcd(this.value, that.value));
        }
        return new BigInt(this.toBigInteger().gcd(other.toBigInteger()));
    }

    @Override
    public int compareTo(long other) {
        return Long.compare(value, other);
    }

    @Override
    public int compareTo(Int other) {
        if (other instanceof Int32 int32){
            return Long.compare(value, int32.value);
        } else if (other instanceof Int32 Int32){
            return Long.compare(value, Int32.value);
        }
        return this.toBigInteger().compareTo(other.toBigInteger());
    }

    @NotNull
    @Override
    public Int negate() {
        return new Int32(-value);
    }

    @Override
    public boolean isZero() {
        return value == 0L;
    }

    @NotNull
    @Override
    public Int plus(@NotNull Int other) {
        try {
            if (other instanceof Int32 Int32) {
                return new Int32( Math.addExact(value , Int32.value));
            } else if (other instanceof Int32 int32) {
                return new Int32( Math.addExact(value , int32.value));
            }
            return new BigInt(this.toBigInteger()).plus(other);
        } catch (ArithmeticException e){
            return new BigInt(this.toBigInteger()).plus(other);
        }
    }

    @Override
    public boolean isOne() {
        return value == 1L;
    }

    @NotNull
    @Override
    public Int times(@NotNull Int other) {
        try {
            if (other instanceof Int32 Int32) {
                return new Int32( Math.multiplyExact(value , Int32.value));
            } else if (other instanceof Int32 int32) {
                return new Int32( Math.multiplyExact(value , int32.value));
            }
            return new BigInt(this.toBigInteger()).times(other);
        } catch (ArithmeticException e){
            return new BigInt(this.toBigInteger()).times(other);
        }
    }

    @Override
    public Int plus(long other) {
        return new Int64(value).plus(other);
    }

    @Override
    public Int minus(long other) {
        return new Int64(value).minus(other);
    }

    @Override
    public Int times(long other) {
        return new Int64(value).times(other);
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof Int64 that){
            return this.value == that.value;
        } else if (other instanceof Int32 that){
            return this.value == that.value;
        } else if (other instanceof Int){
            return this.toBigInteger().compareTo(((Int) other).toBigInteger()) == 0;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return value;
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }
}
