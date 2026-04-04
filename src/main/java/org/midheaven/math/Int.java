package org.midheaven.math;

import org.midheaven.lang.Check;
import org.midheaven.lang.LongOrdered;
import org.midheaven.lang.NotNullable;
import org.midheaven.lang.Nullable;
import org.midheaven.lang.Ordered;
import org.midheaven.lang.Signed;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * Defines the contract for Int.
 */
public sealed interface Int extends Ordered<Int> , LongOrdered, Signed<Int>, AdditionGroup<Int>, MultiplicationMonoid<Int>
    permits Int32, Int64, IntZero, IntOne, IntNegativeOne, BigInt{

    static @NotNullable Arithmetic<Int, Rational> arithmetic(){
        return IntArithmetic.INSTANCE;
    }
    
    @NotNullable Int ZERO = new IntZero();
    @NotNullable Int ONE = new IntOne();
    @NotNullable Int NEGATIVE_ONE = new IntNegativeOne();
    
    static @NotNullable Int of(int value){
        return switch (value){
            case 0 -> ZERO;
            case 1 -> ONE;
            case -1 -> NEGATIVE_ONE;
            default -> new Int32(value);
        };
    }
    
    static @NotNullable Int of(long value){
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
    
    static @Nullable Int of(@Nullable BigInteger value){
        if (value == null){
            return null;
        } else if (value.signum() == 0){
            return ZERO;
        }
        return new BigInt(value).reduce();
    }

    static @Nullable Int parse(@Nullable String number){
        if (number == null || number.isBlank()){
            return null;
        }

        return new BigInt(new BigInteger(number)).reduce();
    }

    boolean isNegativeOne();

    
    default @NotNullable Int abs(){
        return this.sign() < 0 ? this.negate() : this;
    }

    default @NotNullable Int plus(long other) {
        return other == 0 ? this : this.plus(Int.of(other));
    }
    
    default @NotNullable Int plus(int other) {
        return plus((long)other);
    }
    
    default @NotNullable Int minus(Int other) {
        Check.argumentIsNotNull(other, "other");
        return this.plus(other.negate());
    }

    default @NotNullable Int minus(long other) {
        return other == 0 ? this : this.minus(Int.of(other));
    }
    
    default @NotNullable Int minus(int other) {
        return minus((long) other);
    }

    default @NotNullable Int times(long other) {
        if (other == 1L){
            return this;
        } else if (other == 0L){
            return ZERO;
        } else if (other == -1L){
            return this.negate();
        }
        return this.times(Int.of(other));
    }
    
    default @NotNullable Int times(int other) {
       return times((long)other);
    }

    default @NotNullable Rational over(@NotNullable Int other) {
        Objects.requireNonNull(other);
        return DynamicRational.of(this, other);
    }

    default @NotNullable Rational over(long other) {
        if (other == 0L){
            throw ArithmeticExceptions.divisionByZero();
        } else if (other == 1L){
           return this.toRational();
        } else if (other == -1L){
            return this.negate().toRational();
        }
        return DynamicRational.of(this, Int.of(other));
    }
    
    default @NotNullable Rational over(int other) {
        return over((long)other);
    }
    
    @NotNullable BigInteger toBigInteger();

    @NotNullable Int square();
    
    @NotNullable Int cube();
    
    @NotNullable BigDecimal toBigDecimal();

    long toLong();

    int toInt();
    
    @NotNullable Rational toRational();

    default @NotNullable Int raisedTo(int exponent){
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

    default @NotNullable DividerAndRemainder<Int> divideAndRemainder(@NotNullable Int other){
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
    
    @NotNullable Int gcd(@NotNullable Int other);
    
    @NotNullable Int increment();
    
    @NotNullable Int decrement();
    
    @Override
    boolean isZero();
}
