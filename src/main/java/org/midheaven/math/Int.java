package org.midheaven.math;

import org.midheaven.lang.LongOrdered;
import org.midheaven.lang.NotNull;
import org.midheaven.lang.Ordered;
import org.midheaven.lang.Signed;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public interface Int extends Ordered<Int> , LongOrdered, Signed<Int>, AdditionGroup<Int>, MultiplicationMonoid<Int>{

    static @NotNull Arithmetic<Int, Rational> arithmetic(){
        return IntArithmetic.INSTANCE;
    }

    @NotNull Int ZERO = new Int32(0);
    @NotNull Int ONE = new Int32(1);
    @NotNull Int MINUS_ONE = new Int32(-1);
    @NotNull Int TEN = new Int32(10);

    @NotNull
    static Int of(int value){
        return new Int32(value);
    }

    @NotNull
    static Int of(long value){
        return new Int64(value).reduce();
    }

    @NotNull
    static Int of(BigInteger value){
        if (value == null){
            return null;
        }
        return new BigInt(value).reduce();
    }

    static Int parse(String number){
        if (number == null || number.isBlank()){
            return null;
        }

        return new BigInt(new BigInteger(number));
    }

    boolean isNegativeOne();

    @NotNull default Int abs(){
        return this.sign() < 0 ? this.negate() : this;
    }

    default Int plus(long other) {
        return this.plus(Int.of(other));
    }

    default Int minus(Int other) {
        Objects.requireNonNull(other);
        return this.plus(other.negate());
    }

    default Int minus(long other) {
        return this.plus(Int.of(-other));
    }

    default Int times(long other) {
        return this.times(Int.of(other));
    }

    default Rational over(Int other) {
        Objects.requireNonNull(other);
        return IntRational.of(this, other);
    }

    default Rational over(long other) {
        return IntRational.of(this, Int.of(other));
    }

    @NotNull BigInteger toBigInteger();

    @NotNull Int square();

    @NotNull Int cube();

    @NotNull BigDecimal toBigDecimal();

    long toLong();

    int toInt();

    Rational toRational();

    default @NotNull Int raisedTo(int exponent){
        if (exponent < 0) {
            throw new ArithmeticException("Cannot invert an Int");
        }
        return switch (exponent){
            case 0 -> Int.ONE;
            case 1 -> this;
            case 2 -> square();
            case 3 -> cube();
            default -> new BigInt(this.toBigInteger().pow(exponent)).reduce();
        };
    }

    default DividerAndRemainder<Int> divideAndRemainder(Int other){
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
}
