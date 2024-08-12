package org.midheaven.math;

import org.midheaven.lang.NotNull;
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

    @NotNull
    @Override
    public BigInteger toBigInteger() {
        return value;
    }

    @NotNull
    @Override
    public Int square() {
        return new BigInt(this.value.pow(2));
    }

    @NotNull
    @Override
    public Int cube() {
        return new BigInt(this.value.pow(3));
    }

    @NotNull
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

    @NotNull
    @Override
    public Int raisedTo(int exponent) {
        return new BigInt(value.pow(exponent));
    }

    @Override
    public Int gcd(Int other) {
        return new BigInt(this.toBigInteger().gcd(other.toBigInteger()));
    }

    @Override
    public int compareTo(long other) {
        return value.compareTo(BigInteger.valueOf(other));
    }

    @Override
    public int compareTo(Int other) {
        return value.compareTo(other.toBigInteger());
    }

    @NotNull
    @Override
    public Int negate() {
        return new BigInt(value.negate());
    }

    @Override
    public boolean isZero() {
        return value.signum() == 0;
    }

    @NotNull
    @Override
    public Int plus(@NotNull Int other) {
        return new BigInt(value.add(other.toBigInteger()));
    }

    @NotNull
    @Override
    public Int minus(@NotNull Int other) {
        return new BigInt(value.subtract(other.toBigInteger()));
    }

    @Override
    public boolean isOne() {
        return value.equals(BigInteger.ONE);
    }

    @NotNull
    @Override
    public Int times(@NotNull Int other) {
        return new BigInt(value.multiply(other.toBigInteger()));
    }

    public Int reduce() {
        try {
            return new Int32(value.intValueExact());
        } catch (ArithmeticException e){
            try {
                return new Int64(value.longValueExact());
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
