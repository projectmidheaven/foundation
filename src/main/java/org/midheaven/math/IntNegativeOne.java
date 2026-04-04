package org.midheaven.math;

import org.midheaven.lang.Nullable;
import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

@ValueClass
/**
 * Represents Int Negative One.
 */
final class IntNegativeOne implements Int {
    
    @Override
    public boolean equals(Object other){
        return other == this || (other instanceof Int that && that.isNegativeOne());
    }
    
    @Override
    public int hashCode(){
        return -1;
    }
    
    @Override
    public String toString(){
        return "-1";
    }
    
    @Override
    public boolean isNegativeOne() {
        return true;
    }
    
    @Nullable
    @Override
    public BigInteger toBigInteger() {
        return BigInteger.ONE.negate();
    }
    
    @Nullable
    @Override
    public Int square() {
        return Int.ONE;
    }
    
    @Nullable
    @Override
    public Int cube() {
        return this;
    }
    
    @Override
    public @Nullable Int raisedTo(int exponent){
        if (exponent < 0) {
            return this.raisedTo(-exponent);
        } else if (exponent % 2 == 0){
            return Int.ONE;
        } else {
            return this;
        }
    }
    
    @Nullable
    @Override
    public BigDecimal toBigDecimal() {
        return BigDecimal.ONE.negate();
    }
    
    @Override
    public long toLong() {
        return -1L;
    }
    
    @Override
    public int toInt() {
        return -1;
    }
    
    @Override
    public Rational toRational() {
        return Rational.NEGATIVE_ONE;
    }
    
    @Override
    public Int gcd(Int other) {
       return Int.ONE;
    }
    
    @Override
    public Int increment() {
        return Int.ZERO;
    }
    
    @Override
    public Int decrement() {
        return Int.of(-2);
    }
    
    @Override
    public int compareTo(long other) {
        return Long.compare(-1, other);
    }
    
    @Override
    public int compareTo(Int o) {
        return Long.compare(-1, o.toLong());
    }
    
    @Override
    public int sign() {
        return -1;
    }
    
    @Override
    public boolean isPositive(){
        return false;
    }
    
    @Override
    public boolean isNegative(){
        return true;
    }
    
    @Override
    public Int negate() {
        return Int.ONE;
    }
    
    @Override
    public boolean isZero() {
        return false;
    }
    
    @Nullable
    @Override
    public Int plus(@Nullable Int other) {
        return other.decrement();
    }
    
    @Override
    public boolean isOne() {
        return false;
    }
    
    @Nullable
    @Override
    public Int times(@Nullable Int other) {
        return other.negate();
    }
    
    public Int abs(){
        return Int.ONE;
    }
    
    public Int plus(long other) {
        return Int.of(other - 1);
    }
    
    public Int minus(Int other) {
        Objects.requireNonNull(other);
        return other.increment();
    }
    
    public Int minus(long other) {
        return Int.of(-1-other);
    }
    
    public Int times(long other) {
        return Int.of(other).negate();
    }
    
    public Rational over(Int other) {
        if (other.isZero()){
            throw ArithmeticExceptions.divisionByZero();
        }
        return other.toRational().invert().negate();
    }
    
    public Rational over(long other) {
        if (other == 0L){
            throw ArithmeticExceptions.divisionByZero();
        }
        return Rational.of(-1, other);
    }


    public DividerAndRemainder<Int> divideAndRemainder(Int other) {
        if (other.isZero()){
            throw new ArithmeticException("Denominator cannot be zero");
        }

        return new DividerAndRemainder<>(this, Int.ZERO);
    }
}
