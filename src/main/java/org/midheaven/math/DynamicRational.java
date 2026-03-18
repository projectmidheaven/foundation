package org.midheaven.math;

import org.midheaven.lang.HashCode;
import org.midheaven.lang.NotNullable;
import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;

@ValueClass
final class DynamicRational implements Rational{

    static @NotNullable DynamicRational from(@NotNullable Rational other){
        return of(other.numerator(), other.denominator());
    }
    
    static @NotNullable DynamicRational of(@NotNullable Int numerator,@NotNullable Int denominator){
        if (denominator.sign() == 0){
            throw ArithmeticExceptions.divisionByZero();
        } else if (denominator.sign()  < 0) {
            // switch signs
            numerator = numerator.negate();
            denominator = denominator.negate();
        }

        if (denominator.isOne()){
            // gcd division is not necessary
            return new DynamicRational(numerator, denominator);
        }

        var gcd = numerator.gcd(denominator);
        numerator = numerator.divideAndRemainder(gcd).divider();
        denominator  = denominator.divideAndRemainder(gcd).divider();

        return new DynamicRational(numerator, denominator);
    }
    
    final Int numerator;
    final Int denominator;

    DynamicRational(Int numerator, Int denominator){
        this.numerator = numerator;
        this.denominator = denominator;
    }
    
    @Override
    public @NotNullable Int numerator() {
        return numerator;
    }
    
    @Override
    public @NotNullable Int denominator() {
        return denominator;
    }
    
    @Override
    public @NotNullable Rational square() {
        return of(numerator.square(), denominator.square());
    }
    
    @Override
    public @NotNullable Rational cube() {
        return of(numerator.cube(), denominator.cube());
    }
    
    @Override
    public @NotNullable BigDecimal toBigDecimal() {
        return RationalDivisionSpecification.reduceToBigDecimal(this);
    }

    @Override
    public long toLong() {
        return toBigDecimal().longValue();
    }
    
    @Override
    public Rational floor() {
        // ⌊−x⌋=−⌊x⌋−1.
        if (this.isNegative()){
            return this.abs().floor().negate().minus(1);
        }
        return of( // the floor is just the integer division
                this.numerator.divideAndRemainder(this.denominator).divider(),
                Int.ONE
        ).reduce();
    }
    
    @Override
    public Rational ceil() {
        // ⌈x/y⌉=⌊(x−1)/y⌋+1
        // ⌈−x⌉=−⌊x⌋.
        if (this.isNegative()){
            return this.abs().floor().negate();
        }
        return of(
                this.numerator.decrement().divideAndRemainder(this.denominator).divider().increment(),
                Int.ONE
        ).reduce();
    }
    
    @NotNullable Rational reduce() {
        if (this.denominator.isOne() && this.numerator instanceof BigInt bigNumerator){
            var reduced = bigNumerator.reduce();
            if (reduced instanceof BigInt){
                return this;
            } else {
                return new WholeRational(reduced.toLong());
            }
        }
        return this;
    }

    @Override
    public boolean isWhole() {
        return this.denominator.isOne();
    }
    
    @Override
    public Rational increment() {
        // a /b + 1 = a / b + b/ b = (a+b) b
        return DynamicRational.of(numerator.plus(denominator), denominator);
    }
    
    @Override
    public Rational decrement() {
        // a /b - 1 = a / b - b/ b = (a-b) b
        return DynamicRational.of(numerator.minus(denominator), denominator);
    }
    
    @Override
    public int compareTo(long other) {
        if (other == 0){
            return numerator.sign();
        } else if (other == 1){
            return numerator.compareTo(denominator) ;
        } else if (other == -1){
            return numerator.compareTo(denominator.negate()) ;
        }
        return numerator.compareTo(denominator.times(Int.of(other))) ;
    }

    @Override
    public int compareTo(Rational other) {
        return this.numerator.times(other.denominator()).compareTo(this.denominator.times(other.numerator()));
    }

    @Override
    public int sign() {
        return numerator.sign();
    }

    @Override
    public Rational negate() {
        return of(numerator.negate(), denominator);
    }

    @Override
    public boolean isZero() {
        return numerator.isZero();
    }
    
    @Override
    public boolean isPositive() {
        return numerator.isPositive();
    }
    
    @Override
    public boolean isNegative() {
        return numerator.isNegative();
    }
    
    @Override
    public Rational plus(Rational other) {
        return of(
            this.numerator.times(other.denominator()).plus(this.denominator.times(other.numerator())),
            this.denominator.times(other.denominator())
        );
    }
    
    @Override
    public Rational times(Rational other) {
        return of(
            this.numerator.times(other.numerator()),
            this.denominator.times(other.denominator())
        );
    }
    
    @Override
    public Rational over(Rational other) {
        return of(
                this.numerator.times(other.denominator()),
                this.denominator.times(other.numerator())
        );
    }

    @Override
    public Rational invert() {
        if (this.numerator.isPositive()){
            return of(denominator, numerator);
        } else if (numerator.isNegative()){
            return of(denominator.negate(), numerator.negate());
        }
        throw ArithmeticExceptions.divisionByZero();

    }
    
    @Override
    public boolean equals(Object other){
        return other instanceof Rational that
                && this.numerator.equals(that.numerator())
                && this.denominator.equals(that.denominator());
    }

    @Override
    public int hashCode(){
        return HashCode.asymmetric().add(this.numerator).add(this.denominator).hashCode();
    }

    @Override
    public String toString(){
        if (denominator.isOne()){
            return numerator.toString();
        }
        return numerator + "/" + denominator;
    }

}
