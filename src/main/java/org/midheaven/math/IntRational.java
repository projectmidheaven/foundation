package org.midheaven.math;

import org.midheaven.lang.HashCode;
import org.midheaven.lang.NotNull;
import org.midheaven.lang.ValueClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ValueClass
final class IntRational implements Rational{

    static IntRational from(Rational other){
        return of(other.numerator(), other.denominator());
    }
    static IntRational of(Int numerator, Int denominator){
        if (denominator.sign() == 0){
            throw new ArithmeticException("Denominator cannot be zero");
        } else if (denominator.sign()  < 0) {
            // switch signs
            numerator = numerator.negate();
            denominator = denominator.negate();
        }

        if (denominator.isOne()){
            // gcd division is not necessary
            return new IntRational(numerator, denominator);
        }

        var gcd = numerator.gcd(denominator);
        numerator = numerator.divideAndRemainder(gcd).divider();
        denominator  = denominator.divideAndRemainder(gcd).divider();

        return new IntRational(numerator, denominator);
    }
    
    final Int numerator;
    final Int denominator;

    IntRational(Int numerator, Int denominator){
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public boolean isNegativeOne() {
        return false;
    }

    @NotNull
    @Override
    public Int numerator() {
        return numerator;
    }

    @NotNull
    @Override
    public Int denominator() {
        return denominator;
    }

    @NotNull
    @Override
    public Rational square() {
        return of(numerator.square(), denominator.square());
    }

    @NotNull
    @Override
    public Rational cube() {
        return of(numerator.cube(), denominator.cube());
    }

    @NotNull
    @Override
    public Rational raisedTo(int exponent) {
        return of(numerator.raisedTo(exponent), denominator.raisedTo(exponent));
    }

    @Override
    public BigDecimal toBigDecimal() {
        return new BigDecimal(this.numerator.toBigInteger()).divide(new BigDecimal(this.denominator.toBigInteger()), 20, RoundingMode.HALF_DOWN);
    }

    @Override
    public long toLong() {
        return toBigDecimal().longValue();
    }

    @NotNull
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

    @NotNull
    @Override
    public Rational ceil() {
        // ⌈x/y⌉=⌊(x−1)/y⌋+1
        // ⌈−x⌉=−⌊x⌋.
        if (this.isNegative()){
            return this.abs().floor().negate();
        }
        return of(
                Int.ONE.plus(this.numerator.minus(Int.ONE).divideAndRemainder(this.denominator).divider()),
                Int.ONE
        ).reduce();
    }

    @NotNull
    Rational reduce() {
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

    @NotNull
    @Override
    public Rational plus(@NotNull Rational other) {
        return of(
            this.numerator.times(other.denominator()).plus(this.denominator.times(other.numerator())),
            this.denominator.times(other.denominator())
        );
    }

    @NotNull
    @Override
    public Rational times(@NotNull Rational other) {
        return of(
            this.numerator.times(other.numerator()),
            this.denominator.times(other.denominator())
        );
    }

    @NotNull
    @Override
    public Rational over(@NotNull Rational other) {
        return of(
                this.numerator.times(other.denominator()),
                this.denominator.times(other.numerator())
        );
    }

    @Override
    public Rational invert() {
        if (numerator.isNegative()){
            return of(denominator.negate(), numerator.negate());
        }
        return of(denominator, numerator);
    }

    @Override
    public boolean isOne() {
        return numerator.equals(denominator);
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
