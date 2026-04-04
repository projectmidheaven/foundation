package org.midheaven.math;

import org.midheaven.lang.Nullable;
import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

@ValueClass
/**
 * Represents Int One.
 */
final class IntOne implements Int {
    
    @Override
    public boolean equals(Object other){
        return other == this || (other instanceof Int that && that.isOne());
    }
    
    @Override
    public int hashCode(){
        return 1;
    }
    
    @Override
    public String toString(){
        return "1";
    }
    
    @Override
    public boolean isNegativeOne() {
        return false;
    }
    
    @Nullable
    @Override
    public BigInteger toBigInteger() {
        return BigInteger.ONE;
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
        return BigDecimal.ONE;
    }
    
    @Override
    public long toLong() {
        return 1L;
    }
    
    @Override
    public int toInt() {
        return 1;
    }
    
    @Override
    public Rational toRational() {
        return Rational.ONE;
    }
    
    @Override
    public Int gcd(Int other) {
        return this;
    }
    
    @Override
    public Int increment() {
        return Int.of(2);
    }
    
    @Override
    public Int decrement() {
        return Int.ZERO;
    }
    
    @Override
    public int compareTo(long other) {
        return Long.compare(1, other);
    }
    
    @Override
    public int compareTo(Int o) {
        return Long.compare(1, o.toLong());
    }
    
    @Override
    public int sign() {
        return 1;
    }
    
    @Override
    public boolean isPositive(){
        return true;
    }
    
    @Override
    public boolean isNegative(){
        return false;
    }
    
    @Override
    public Int negate() {
        return Int.NEGATIVE_ONE;
    }
    
    @Override
    public boolean isZero() {
        return false;
    }
    
    @Nullable
    @Override
    public Int plus(@Nullable Int other) {
        return other.increment();
    }
    
    @Override
    public boolean isOne() {
        return true;
    }
    
    @Nullable
    @Override
    public Int times(@Nullable Int other) {
        return other;
    }
    
    
    public Int abs(){
        return this;
    }
    
    public Int plus(long other) {
        return Int.of(other + 1);
    }
    
    public Int minus(Int other) {
        Objects.requireNonNull(other);
        return other.decrement();
    }
    
    public Int minus(long other) {
        return Int.of(1-other);
    }
    
    public Int times(long other) {
        return Int.of(other);
    }
    
    public Rational over(Int other) {
        if (other.isZero()){
            throw ArithmeticExceptions.divisionByZero();
        }
        return other.toRational().invert();
    }
    
    public Rational over(long other) {
        if (other == 0L){
            throw ArithmeticExceptions.divisionByZero();
        }
        return Rational.of(1, other);
    }

    public @Nullable Int raisedTo(int exponent){
        return this;
    }
    
    public DividerAndRemainder<Int> divideAndRemainder(Int other) {
        if (other.isZero()){
            throw new ArithmeticException("Denominator cannot be zero");
        }

        return new DividerAndRemainder<>(this, Int.ZERO);
    }
}
