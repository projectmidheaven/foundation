package org.midheaven.math;

import org.midheaven.lang.NotNull;
import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;
import java.math.BigInteger;

@ValueClass
public final class Int64 implements Int {

    final long value;

    Int64(long value) {
        this.value = value;
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
        return Long.signum(value);
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
            return new Int64(Math.multiplyExact(value, value));
        } catch (ArithmeticException e){
            return new BigInt(BigInteger.valueOf(value)).square();
        }
    }

    @NotNull
    @Override
    public Int cube() {
        try {
            return new Int64(Math.multiplyExact(value, Math.multiplyExact(value, value)));
        } catch (ArithmeticException e){
            return new BigInt(BigInteger.valueOf(value)).cube();
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
        return this.toBigInteger().intValueExact();
    }

    @Override
    public Rational toRational() {
        return Rational.of(value);
    }

    @Override
    public int compareTo(long other) {
        return Long.compare(value, other);
    }

    @Override
    public int compareTo(Int other) {
        if (other instanceof Int32 int32){
            return Long.compare(value, int32.value);
        } else if (other instanceof Int64 int64){
            return Long.compare(value, int64.value);
        }
        return this.toBigInteger().compareTo(other.toBigInteger());
    }

    @NotNull
    @Override
    public Int negate() {
        return new Int64(-value);
    }

    @Override
    public boolean isZero() {
        return value == 0L;
    }

    @NotNull
    @Override
    public Int plus(@NotNull Int other) {
        try {
            if (other instanceof Int64 int64) {
                return new Int64( Math.addExact(value , int64.value));
            } else if (other instanceof Int32 int32) {
                return new Int64( Math.addExact(value , int32.value));
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
            if (other instanceof Int64 int64) {
                return new Int64( Math.multiplyExact(value , int64.value));
            } else if (other instanceof Int32 int32) {
                return new Int64( Math.multiplyExact(value , int32.value));
            }
            return new BigInt(this.toBigInteger()).times(other);
        } catch (ArithmeticException e){
            return new BigInt(this.toBigInteger()).times(other);
        }
    }

    public Int reduce(){
        if (value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE){
            return new Int32((int)value);
        }
        return this;
    }

    @Override
    public Int plus(long other) {
        try {
            return new Int64(Math.addExact(value, other));
        } catch (ArithmeticException e){
            return new BigInt(this.toBigInteger().add(BigInteger.valueOf(other)));
        }
    }

    @Override
    public Int minus(long other) {
        try {
            return new Int64(Math.subtractExact(value, other));
        } catch (ArithmeticException e){
            return new BigInt(this.toBigInteger().subtract(BigInteger.valueOf(other)));
        }
    }

    @Override
    public Int times(long other) {
        try {
            return new Int64(Math.multiplyExact(value, other));
        } catch (ArithmeticException e){
            return new BigInt(this.toBigInteger().multiply(BigInteger.valueOf(other)));
        }
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
    public Int gcd(Int other) {
        if (other instanceof Int64 that){
            return new Int64(Numbers.gcd(this.value, that.value));
        } else if (other instanceof Int32 that){
            return new Int64(Numbers.gcd(this.value, that.value));
        }
        return new BigInt(this.toBigInteger().gcd(other.toBigInteger()));
    }

    @Override
    public int hashCode(){
        return Long.hashCode(value);
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }
}
