package org.midheaven.math;

import org.midheaven.lang.Nullable;
import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

@ValueClass
/**
 * Represents Int32.
 */
public final class Int32 implements Int {

    final int value;

    Int32(int value) {
        this.value = value;
    }

   
    /**
     * Performs over.
     * @param other the other value
     * @return the result of over
     */
    @Override
    /**
     * Performs over.
     * @param other the other value
     * @return the result of over
     */
    public Rational over(Int other) {
        Objects.requireNonNull(other);
        return DynamicRational.of(this, other);
    }

   
    /**
     * Performs over.
     * @param other the other value
     * @return the result of over
     */
    @Override
    /**
     * Performs over.
     * @param other the other value
     * @return the result of over
     */
    public Rational over(long other) {
        return Rational.of(value, other);
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
        return value == -1L;
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
        return Integer.signum(value);
    }

   
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
        return BigInteger.valueOf(value);
    }

   
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
        try {
            return new Int32(Math.multiplyExact(value, value));
        } catch (ArithmeticException e){
            return promote().square();
        }
    }
    
    /**
     * Performs promote.
     * @return the result of promote
     */
    private Int promote(){
        return Int64.fromInt(value);
    }

   
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
        try {
            return new Int32(Math.multiplyExact(value, Math.multiplyExact(value, value)));
        } catch (ArithmeticException e){
            return promote().cube();
        }
    }

   
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
        return BigDecimal.valueOf(value);
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
        return value;
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
        return value;
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
        return Rational.of(value);
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
        return switch (other){
            case IntZero ignore-> Int.of(Numbers.gcd(this.value,0));
            case IntOne ignore ->  Int.of(Numbers.gcd(this.value,1));
            case IntNegativeOne ignore ->  Int.of(Numbers.gcd(this.value,-1));
            case Int64 int64 ->  Int.of(Numbers.gcd(this.value, int64.value));
            case Int32 int32 ->  Int.of(Numbers.gcd(this.value, int32.value));
            case BigInt bigInt ->  new BigInt(this.toBigInteger().gcd(bigInt.toBigInteger())).reduce();
        };
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
        try {
            return Int.of(Math.incrementExact(value));
        } catch (ArithmeticException e){
            return promote().increment();
        }
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
        try {
            return Int.of(Math.decrementExact(value));
        } catch (ArithmeticException e){
            return promote().decrement();
        }
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
        return Long.compare(value, other);
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
        return switch (other) {
            case Int64 int64 -> Long.compare(this.value, int64.value);
            case Int32 int32 -> Long.compare(this.value, int32.value);
            default -> this.toBigInteger().compareTo(other.toBigInteger());
        };
    }

   
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
        if (value < Integer.MAX_VALUE){
            return new Int32(-value);
        }
         return promote().negate();
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
        return value == 0;
    }
    
    /**
     * Checks whether is Positive.
     * @return the result of isPositive
     */
    @Override
    /**
     * Checks whether is Positive.
     * @return the result of isPositive
     */
    public boolean isPositive() {
        return value > 0;
    }
    
    /**
     * Checks whether is Negative.
     * @return the result of isNegative
     */
    @Override
    /**
     * Checks whether is Negative.
     * @return the result of isNegative
     */
    public boolean isNegative() {
        return value < 0;
    }
   
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
        try {
            return switch (other){
                case IntZero ignore -> this;
                case IntOne ignore -> this.increment();
                case IntNegativeOne ignore -> this.decrement();
                case Int64 int64 ->  new Int64( Math.addExact(value , int64.value));
                case Int32 int32 ->  new Int32( Math.addExact(value , int32.value));
                case BigInt bigInt -> bigInt.plus(this);
            };
        } catch (ArithmeticException e){
            return promote().plus(other);
        }
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
        return value == 1L;
    }

   
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
        try {
            return switch (other){
                case IntZero z-> z;
                case IntOne ignore -> this;
                case IntNegativeOne ignore -> this.negate();
                case Int64 int64 ->  new Int64( Math.multiplyExact(value , int64.value));
                case Int32 int32 ->  new Int64( Math.multiplyExact(value , int32.value));
                case BigInt bigInt -> bigInt.times(this);
            };
        } catch (ArithmeticException e){
            return promote().times(other);
        }
    }

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
    public Int plus(long other) {
        try {
            return new Int64(Math.addExact(value , other));
        } catch (ArithmeticException e){
            return promote().plus(other);
        }
    }
    
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
    public Int plus(int other) {
        try {
            return new Int32(Math.addExact(value , other));
        } catch (ArithmeticException e){
            return promote().plus(other);
        }
    }

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
    public Int minus(long other) {
        try {
            return new Int64(Math.subtractExact(value , other));
        } catch (ArithmeticException e){
            return promote().minus(other);
        }
    }
    
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
    public Int minus(int other) {
        try {
            return new Int32(Math.subtractExact(value , other));
        } catch (ArithmeticException e){
            return promote().minus(other);
        }
    }

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
    public Int times(long other) {
        try {
            return new Int64(Math.multiplyExact(value , other));
        } catch (ArithmeticException e){
            return promote().times(other);
        }
    }
    
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
    public Int times(int other) {
        try {
            return new Int32(Math.multiplyExact(value , other));
        } catch (ArithmeticException e){
            return promote().times(other);
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
        return (other instanceof Int i) && switch (i){
            case IntZero z-> this.isZero();
            case IntOne ignore -> this.isOne();
            case IntNegativeOne ignore -> this.isNegativeOne();
            case Int64 int64 -> this.value == int64.value;
            case Int32 int32 -> this.value == int32.value;
            case BigInt bigInt -> this.toBigInteger().compareTo(bigInt.value) == 0;
        };
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
        return value;
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
        return String.valueOf(value);
    }
}
