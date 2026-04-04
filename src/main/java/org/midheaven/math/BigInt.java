package org.midheaven.math;

import org.midheaven.lang.Nullable;
import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;
import java.math.BigInteger;

@ValueClass
/**
 * Represents Big Int.
 */
public final class BigInt implements Int {

    final BigInteger value;

    BigInt(BigInteger value) {
        this.value = value;
    }

    /**
     * Checks whether is Negative One.
     * @return the result of isNegativeOne
     */
    @Override
    /**
     * Checks whether is Negative One.
     * @return the result of isNegativeOne
     */
    public boolean isNegativeOne() {
        return value.signum() < 0 && value.equals(BigInteger.ONE.negate());
    }

    /**
     * Performs sign.
     * @return the result of sign
     */
    @Override
    /**
     * Performs sign.
     * @return the result of sign
     */
    public int sign() {
        return value.signum();
    }

    /**
     * Returns to Big Integer.
     * @return the result of toBigInteger
     */
    @Nullable
    /**
     * Returns to Big Integer.
     * @return the result of toBigInteger
     */
    @Override
    /**
     * Returns to Big Integer.
     * @return the result of toBigInteger
     */
    public BigInteger toBigInteger() {
        return value;
    }

    /**
     * Performs square.
     * @return the result of square
     */
    @Nullable
    /**
     * Performs square.
     * @return the result of square
     */
    @Override
    /**
     * Performs square.
     * @return the result of square
     */
    public Int square() {
        return new BigInt(this.value.pow(2));
    }

    /**
     * Performs cube.
     * @return the result of cube
     */
    @Nullable
    /**
     * Performs cube.
     * @return the result of cube
     */
    @Override
    /**
     * Performs cube.
     * @return the result of cube
     */
    public Int cube() {
        return new BigInt(this.value.pow(3));
    }

    /**
     * Returns to Big Decimal.
     * @return the result of toBigDecimal
     */
    @Nullable
    /**
     * Returns to Big Decimal.
     * @return the result of toBigDecimal
     */
    @Override
    /**
     * Returns to Big Decimal.
     * @return the result of toBigDecimal
     */
    public BigDecimal toBigDecimal() {
        return new BigDecimal(this.value.toString());
    }

    /**
     * Returns to Long.
     * @return the result of toLong
     */
    @Override
    /**
     * Returns to Long.
     * @return the result of toLong
     */
    public long toLong() {
        return value.longValueExact();
    }

    /**
     * Returns to Int.
     * @return the result of toInt
     */
    @Override
    /**
     * Returns to Int.
     * @return the result of toInt
     */
    public int toInt() {
        return value.intValueExact();
    }

    /**
     * Returns to Rational.
     * @return the result of toRational
     */
    @Override
    /**
     * Returns to Rational.
     * @return the result of toRational
     */
    public Rational toRational() {
        return Rational.of(this.value);
    }

    /**
     * Performs raisedTo.
     * @param exponent the exponent value
     * @return the result of raisedTo
     */
    @Nullable
    /**
     * Performs raisedTo.
     * @param exponent the exponent value
     * @return the result of raisedTo
     */
    @Override
    /**
     * Performs raisedTo.
     * @param exponent the exponent value
     * @return the result of raisedTo
     */
    public Int raisedTo(int exponent) {
        return new BigInt(value.pow(exponent));
    }

    /**
     * Performs gcd.
     * @param other the other value
     * @return the result of gcd
     */
    @Override
    /**
     * Performs gcd.
     * @param other the other value
     * @return the result of gcd
     */
    public Int gcd(Int other) {
        return new BigInt(this.toBigInteger().gcd(other.toBigInteger()));
    }
    
    /**
     * Performs increment.
     * @return the result of increment
     */
    @Override
    /**
     * Performs increment.
     * @return the result of increment
     */
    public Int increment() {
        return new BigInt(value.add(BigInteger.ONE)).reduce();
    }
    
    /**
     * Performs decrement.
     * @return the result of decrement
     */
    @Override
    /**
     * Performs decrement.
     * @return the result of decrement
     */
    public Int decrement() {
        return new BigInt(value.subtract(BigInteger.ONE)).reduce();
    }
    
    /**
     * Performs compareTo.
     * @param other the other value
     * @return the result of compareTo
     */
    @Override
    /**
     * Performs compareTo.
     * @param other the other value
     * @return the result of compareTo
     */
    public int compareTo(long other) {
        return value.compareTo(BigInteger.valueOf(other));
    }

    /**
     * Performs compareTo.
     * @param other the other value
     * @return the result of compareTo
     */
    @Override
    /**
     * Performs compareTo.
     * @param other the other value
     * @return the result of compareTo
     */
    public int compareTo(Int other) {
        return value.compareTo(other.toBigInteger());
    }

    /**
     * Performs negate.
     * @return the result of negate
     */
    @Nullable
    /**
     * Performs negate.
     * @return the result of negate
     */
    @Override
    /**
     * Performs negate.
     * @return the result of negate
     */
    public Int negate() {
        return new BigInt(value.negate());
    }

    /**
     * Checks whether is Zero.
     * @return the result of isZero
     */
    @Override
    /**
     * Checks whether is Zero.
     * @return the result of isZero
     */
    public boolean isZero() {
        return value.signum() == 0;
    }

    /**
     * Performs plus.
     * @param other the other value
     * @return the result of plus
     */
    @Nullable
    /**
     * Performs plus.
     * @param other the other value
     * @return the result of plus
     */
    @Override
    /**
     * Performs plus.
     * @param other the other value
     * @return the result of plus
     */
    public Int plus(@Nullable Int other) {
        return new BigInt(value.add(other.toBigInteger()));
    }

    /**
     * Performs minus.
     * @param other the other value
     * @return the result of minus
     */
    @Nullable
    /**
     * Performs minus.
     * @param other the other value
     * @return the result of minus
     */
    @Override
    /**
     * Performs minus.
     * @param other the other value
     * @return the result of minus
     */
    public Int minus(@Nullable Int other) {
        return new BigInt(value.subtract(other.toBigInteger()));
    }

    /**
     * Checks whether is One.
     * @return the result of isOne
     */
    @Override
    /**
     * Checks whether is One.
     * @return the result of isOne
     */
    public boolean isOne() {
        return value.equals(BigInteger.ONE);
    }

    /**
     * Performs times.
     * @param other the other value
     * @return the result of times
     */
    @Nullable
    /**
     * Performs times.
     * @param other the other value
     * @return the result of times
     */
    @Override
    /**
     * Performs times.
     * @param other the other value
     * @return the result of times
     */
    public Int times(@Nullable Int other) {
        return new BigInt(value.multiply(other.toBigInteger()));
    }

    /**
     * Performs reduce.
     * @return the result of reduce
     */
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

    /**
     * Performs equals.
     * @param other the other value
     * @return the result of equals
     */
    @Override
    /**
     * Performs equals.
     * @param other the other value
     * @return the result of equals
     */
    public boolean equals(Object other){
        return other instanceof Int that
            && that.toBigInteger().compareTo(value) == 0;
    }

    /**
     * Checks whether hash Code.
     * @return the result of hashCode
     */
    @Override
    /**
     * Checks whether hash Code.
     * @return the result of hashCode
     */
    public int hashCode(){
        return value.hashCode();
    }

    /**
     * Returns to String.
     * @return the result of toString
     */
    @Override
    /**
     * Returns to String.
     * @return the result of toString
     */
    public String toString(){
        return value.toString();
    }
}
