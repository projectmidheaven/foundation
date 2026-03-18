package org.midheaven.math;

import org.midheaven.lang.Nullable;
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

   
    @Override
    public Rational over(Int other) {
        Objects.requireNonNull(other);
        return DynamicRational.of(this, other);
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
        return Integer.signum(value);
    }

   
    @Override
    public BigInteger toBigInteger() {
        return BigInteger.valueOf(value);
    }

   
    @Override
    public Int square() {
        try {
            return new Int32(Math.multiplyExact(value, value));
        } catch (ArithmeticException e){
            return promote().square();
        }
    }
    
    private Int promote(){
        return Int64.fromInt(value);
    }

   
    @Override
    public Int cube() {
        try {
            return new Int32(Math.multiplyExact(value, Math.multiplyExact(value, value)));
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
        return value;
    }

    @Override
    public Rational toRational() {
        return Rational.of(value);
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
            return Int.of(Math.incrementExact(value));
        } catch (ArithmeticException e){
            return promote().increment();
        }
    }
    
    @Override
    public Int decrement() {
        try {
            return Int.of(Math.decrementExact(value));
        } catch (ArithmeticException e){
            return promote().decrement();
        }
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
        if (value < Integer.MAX_VALUE){
            return new Int32(-value);
        }
         return promote().negate();
    }

    @Override
    public boolean isZero() {
        return value == 0;
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

    @Override
    public boolean isOne() {
        return value == 1L;
    }

   
    @Override
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

    @Override
    public Int plus(long other) {
        try {
            return new Int64(Math.addExact(value , other));
        } catch (ArithmeticException e){
            return promote().plus(other);
        }
    }
    
    @Override
    public Int plus(int other) {
        try {
            return new Int32(Math.addExact(value , other));
        } catch (ArithmeticException e){
            return promote().plus(other);
        }
    }

    @Override
    public Int minus(long other) {
        try {
            return new Int64(Math.subtractExact(value , other));
        } catch (ArithmeticException e){
            return promote().minus(other);
        }
    }
    
    @Override
    public Int minus(int other) {
        try {
            return new Int32(Math.subtractExact(value , other));
        } catch (ArithmeticException e){
            return promote().minus(other);
        }
    }

    @Override
    public Int times(long other) {
        try {
            return new Int64(Math.multiplyExact(value , other));
        } catch (ArithmeticException e){
            return promote().times(other);
        }
    }
    
    @Override
    public Int times(int other) {
        try {
            return new Int32(Math.multiplyExact(value , other));
        } catch (ArithmeticException e){
            return promote().times(other);
        }
    }

    @Override
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

    @Override
    public int hashCode(){
        return value;
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }
}
