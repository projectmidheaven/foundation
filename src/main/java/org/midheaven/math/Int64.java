package org.midheaven.math;

import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;
import java.math.BigInteger;

@ValueClass
public final class Int64 implements Int {
    
    static Int64 fromInt(int value) {
        return new Int64(value);
    }
    
    final long value;

    Int64(long value) {
        this.value = value;
    }

    
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
    
    @Override
    public BigInteger toBigInteger() {
        return BigInteger.valueOf(value);
    }
    
    @Override
    public Int square() {
        try {
            return new Int64(Math.multiplyExact(value, value));
        } catch (ArithmeticException e){
            return promote().square();
        }
    }
    
    private Int promote(){
        return new BigInt(BigInteger.valueOf(value));
    }

    
    @Override
    public Int cube() {
        try {
            return Int.of(Math.multiplyExact(value, Math.multiplyExact(value, value)));
        } catch (ArithmeticException e){
            return promote().cube();
        }
    }

    
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
        return switch (other) {
            case Int64 int64 -> Long.compare(this.value, int64.value);
            case Int32 int32 -> Long.compare(this.value, int32.value);
            default -> this.toBigInteger().compareTo(other.toBigInteger());
        };
    }

    
    @Override
    public Int negate() {
        if (value > Long.MIN_VALUE){
            return new Int64(-value);
        }
       return promote().negate();
    }

    @Override
    public boolean isZero() {
        return value == 0L;
    }
    
    @Override
    public boolean isPositive() {
        return value > 0;
    }
    
    @Override
    public boolean isNegative() {
        return value < 0;
    }
    
    
    @Override
    public Int plus( Int other) {
        try {
            return switch (other){
                case IntZero ignore -> this;
                case IntOne ignore -> this.increment();
                case IntNegativeOne ignore -> this.decrement();
                case Int64 int64 ->  new Int64( Math.addExact(value , int64.value));
                case Int32 int32 ->  new Int64( Math.addExact(value , int32.value));
                case BigInt bigInt -> bigInt.plus(this);
            };
        } catch (ArithmeticException e){
            return promote().plus(other);
        }
    }

    @Override
    public boolean isOne() {
        return value == 1L;
    }

    
    @Override
    public Int times( Int other) {
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
        return (other instanceof Int i) && switch (i){
            case IntZero ignore-> this.isZero();
            case IntOne ignore -> this.isOne();
            case IntNegativeOne ignore -> this.isNegativeOne();
            case Int64 int64 ->  this.value == int64.value;
            case Int32 int32 ->  this.value == int32.value;
            case BigInt bigInt -> this.toBigInteger().compareTo(bigInt.toBigInteger()) == 0;
        };
    }

    @Override
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
    
    @Override
    public Int increment() {
        try {
            return new Int64(Math.incrementExact(value));
        } catch (ArithmeticException e){
            return new BigInt(this.toBigInteger().add(BigInteger.ONE));
        }
    }
    
    @Override
    public Int decrement() {
        try {
            return new Int64(Math.decrementExact(value));
        } catch (ArithmeticException e){
            return new BigInt(this.toBigInteger().subtract(BigInteger.ONE));
        }
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
