package org.midheaven.math;

import org.midheaven.lang.Nullable;
import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;
import java.math.BigInteger;

@ValueClass
public final class BigInt implements Int {

    final BigInteger value;

    BigInt(BigInteger value) {
        this.value = value;
    }

    @Override
    public boolean isNegativeOne() {
        return value.signum() < 0 && value.equals(BigInteger.ONE.negate());
    }

    @Override
    public int sign() {
        return value.signum();
    }

    @Nullable
    @Override
    public BigInteger toBigInteger() {
        return value;
    }

    @Nullable
    @Override
    public Int square() {
        return new BigInt(this.value.pow(2));
    }

    @Nullable
    @Override
    public Int cube() {
        return new BigInt(this.value.pow(3));
    }

    @Nullable
    @Override
    public BigDecimal toBigDecimal() {
        return new BigDecimal(this.value.toString());
    }

    @Override
    public long toLong() {
        return value.longValueExact();
    }

    @Override
    public int toInt() {
        return value.intValueExact();
    }

    @Override
    public Rational toRational() {
        return Rational.of(this.value);
    }

    @Nullable
    @Override
    public Int raisedTo(int exponent) {
        return new BigInt(value.pow(exponent));
    }

    @Override
    public Int gcd(Int other) {
        return new BigInt(this.toBigInteger().gcd(other.toBigInteger()));
    }
    
    @Override
    public Int increment() {
        return new BigInt(value.add(BigInteger.ONE)).reduce();
    }
    
    @Override
    public Int decrement() {
        return new BigInt(value.subtract(BigInteger.ONE)).reduce();
    }
    
    @Override
    public int compareTo(long other) {
        return value.compareTo(BigInteger.valueOf(other));
    }

    @Override
    public int compareTo(Int other) {
        return value.compareTo(other.toBigInteger());
    }

    @Nullable
    @Override
    public Int negate() {
        return new BigInt(value.negate());
    }

    @Override
    public boolean isZero() {
        return value.signum() == 0;
    }

    @Nullable
    @Override
    public Int plus(@Nullable Int other) {
        return new BigInt(value.add(other.toBigInteger()));
    }

    @Nullable
    @Override
    public Int minus(@Nullable Int other) {
        return new BigInt(value.subtract(other.toBigInteger()));
    }

    @Override
    public boolean isOne() {
        return value.equals(BigInteger.ONE);
    }

    @Nullable
    @Override
    public Int times(@Nullable Int other) {
        return new BigInt(value.multiply(other.toBigInteger()));
    }

    public Int reduce() {
        try {
            return Int.of(value.intValueExact());
        } catch (ArithmeticException e){
            try {
                return Int.of(value.longValueExact());
            } catch (ArithmeticException e2){
                return this;
            }
        }
    }

    @Override
    public boolean equals(Object other){
        return other instanceof Int that
            && that.toBigInteger().compareTo(value) == 0;
    }

    @Override
    public int hashCode(){
        return value.hashCode();
    }

    @Override
    public String toString(){
        return value.toString();
    }
}
