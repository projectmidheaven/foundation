package org.midheaven.math;

import org.midheaven.lang.Nullable;
import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

@ValueClass
/**
 * Represents Int Zero.
 */
final class IntZero implements Int{
    
    @Override
    public boolean equals(Object other){
        return other == this || (other instanceof Int that && that.isZero());
    }
    
    @Override
    public int hashCode(){
        return 0;
    }
    
    @Override
    public String toString(){
        return "0";
    }
    
    @Override
    public boolean isNegativeOne() {
        return false;
    }
    
    @Nullable
    @Override
    public BigInteger toBigInteger() {
        return BigInteger.ZERO;
    }
    
    @Nullable
    @Override
    public Int square() {
        return this;
    }
    
    @Nullable
    @Override
    public Int cube() {
        return this;
    }
    
    @Nullable
    @Override
    public BigDecimal toBigDecimal() {
        return BigDecimal.ZERO;
    }
    
    @Override
    public long toLong() {
        return 0;
    }
    
    @Override
    public int toInt() {
        return 0;
    }
    
    @Override
    public boolean isPositive(){
        return false;
    }
    
    @Override
    public boolean isNegative(){
        return false;
    }
    
    @Override
    public Rational toRational() {
        return Rational.ZERO;
    }
    
    @Override
    public Int gcd(Int other) {
        return other.abs();
    }
    
    @Override
    public Int increment() {
        return Int.ONE;
    }
    
    @Override
    public Int decrement() {
        return Int.NEGATIVE_ONE;
    }
    
    @Override
    public int compareTo(long other) {
        return Long.compare(0, other);
    }
    
    @Override
    public int compareTo(Int o) {
        return -o.sign();
    }
    
    @Override
    public int sign() {
        return 0;
    }
    
    @Override
    public Int negate() {
        return this;
    }
    
    @Override
    public boolean isZero() {
        return true;
    }
    
    @Nullable
    @Override
    public Int plus(@Nullable Int other) {
        return other;
    }
    
    @Override
    public boolean isOne() {
        return false;
    }
    
    @Nullable
    @Override
    public Int times(@Nullable Int other) {
        return this;
    }
    
    
    public Int abs(){
        return this;
    }
    
    public Int plus(long other) {
        return other == 0 ? this : Int.of(other);
    }
    
    public Int minus(Int other) {
        Objects.requireNonNull(other);
        return other.negate();
    }
    
    public Int minus(long other) {
        return other == 0 ? this : Int.of(-other);
    }
    
    public Int times(long other) {
        return ZERO;
    }
    
    public Rational over(Int other) {
        if (other.isZero()){
            throw ArithmeticExceptions.divisionByZero();
        }
        return Rational.ZERO;
    }
    
    public Rational over(long other) {
        if (other == 0L){
            throw ArithmeticExceptions.divisionByZero();
        }
        return Rational.ZERO;
    }

    public @Nullable Int raisedTo(int exponent){
        if (exponent < 0) {
            throw ArithmeticExceptions.integerInversion();
        } else if (exponent == 0){
            return Int.ONE;
        }
        return this;
    }
    
    public DividerAndRemainder<Int> divideAndRemainder(Int other) {
        if (other.isZero()){
            throw new ArithmeticException("Denominator cannot be zero");
        }

        return new DividerAndRemainder<>(this, this);
    }
}
