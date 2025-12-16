package org.midheaven.math;

import org.midheaven.lang.LongOrdered;
import org.midheaven.lang.Nullable;
import org.midheaven.lang.Ordered;
import org.midheaven.lang.Signed;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public sealed interface Int extends Ordered<Int> , LongOrdered, Signed<Int>, AdditionGroup<Int>, MultiplicationMonoid<Int>
    permits Int32, Int64, IntZero, IntOne, IntNegativeOne, BigInt{

    static @Nullable Arithmetic<Int, Rational> arithmetic(){
        return IntArithmetic.INSTANCE;
    }
    
    @Nullable
    Int ZERO = new IntZero();
    @Nullable
    Int ONE = new IntOne();
    @Nullable
    Int NEGATIVE_ONE = new IntNegativeOne();

    @Nullable
    static Int of(int value){
        return switch (value){
            case 0 -> ZERO;
            case 1 -> ONE;
            case -1 -> NEGATIVE_ONE;
            default -> new Int32(value);
        };
    }

    @Nullable
    static Int of(long value){
        if (value == 0L){
            return ZERO;
        } else if (value == 1L){
            return ONE;
        }  else if (value == -1L){
            return NEGATIVE_ONE;
        } else if (value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE){
            return Int.of((int)value);
        }
        return new Int64(value);
    }
    
    static Int of(BigInteger value){
        if (value == null){
            return null;
        } else if (value.signum() == 0){
            return ZERO;
        }
        return new BigInt(value).reduce();
    }

    static Int parse(String number){
        if (number == null || number.isBlank()){
            return null;
        }

        return new BigInt(new BigInteger(number)).reduce();
    }

    boolean isNegativeOne();

    @Nullable
    default Int abs(){
        return this.sign() < 0 ? this.negate() : this;
    }

    default Int plus(long other) {
        return other == 0 ? this : this.plus(Int.of(other));
    }
    
    default Int plus(int other) {
        return plus((long)other);
    }
    
    default Int minus(Int other) {
        Objects.requireNonNull(other);
        return this.plus(other.negate());
    }

    default Int minus(long other) {
        return other == 0 ? this : this.minus(Int.of(other));
    }
    
    default Int minus(int other) {
        return minus((long) other);
    }

    default Int times(long other) {
        if (other == 1L){
            return this;
        } else if (other == 0L){
            return ZERO;
        } else if (other == -1L){
            return this.negate();
        }
        return this.times(Int.of(other));
    }
    
    default Int times(int other) {
       return times((long)other);
    }

    default Rational over(Int other) {
        Objects.requireNonNull(other);
        return DynamicRational.of(this, other);
    }

    default Rational over(long other) {
        if (other == 0L){
            throw ArithmeticExceptions.divisionByZero();
        } else if (other == 1L){
           return this.toRational();
        } else if (other == -1L){
            return this.negate().toRational();
        }
        return DynamicRational.of(this, Int.of(other));
    }
    
    default Rational over(int other) {
        return over((long)other);
    }
    
    @Nullable
    BigInteger toBigInteger();

    @Nullable
    Int square();

    @Nullable
    Int cube();

    @Nullable
    BigDecimal toBigDecimal();

    long toLong();

    int toInt();

    Rational toRational();

    default @Nullable Int raisedTo(int exponent){
        if (exponent < 0) {
            throw ArithmeticExceptions.integerInversion();
        }
        return switch (exponent){
            case 0 -> Int.ONE; // 0^0 = 1 by convention
            case 1 -> this;
            case 2 -> square();
            case 3 -> cube();
            default -> new BigInt(this.toBigInteger().pow(exponent)).reduce();
        };
    }

    default DividerAndRemainder<Int> divideAndRemainder(Int other){
        if (other.isZero()){
            throw ArithmeticExceptions.divisionByZero();
        }
        if (!(this instanceof BigInt) && !(other instanceof BigInt)){
            try {
                return new DividerAndRemainder<>(
                        Int.of(  Math.divideExact(this.toLong() , other.toLong())),
                        Int.of(this.toLong() % other.toLong())
                );
            } catch (ArithmeticException e) {
                // fall thought use bigInteger
            }
        }
        var dividerAndRemainder = this.toBigInteger().divideAndRemainder(other.toBigInteger());

        return new DividerAndRemainder<>(
            Int.of(dividerAndRemainder[0]),
            Int.of(dividerAndRemainder[1])
        );
    }

    Int gcd(Int other);
    
    Int increment();
    
    Int decrement();
}
