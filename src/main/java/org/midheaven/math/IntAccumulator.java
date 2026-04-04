package org.midheaven.math;

import org.midheaven.lang.Ordered;
import org.midheaven.lang.Signed;

import java.math.BigInteger;

/**
 * Represents Int Accumulator.
 */
public final class IntAccumulator implements Ordered<Int>, Signed<IntAccumulator> {
    
    Int value;

    /**
     * Creates a new IntAccumulator.
     */
    public IntAccumulator(){
        value = Int.ZERO;
    }

    /**
     * Creates a new IntAccumulator.
     * @param initialValue the initialValue value
     */
    public IntAccumulator(Int initialValue){
         value = initialValue;
    }

    /**
     * Creates a new IntAccumulator.
     * @param other the other value
     */
    public IntAccumulator(IntAccumulator other){
        this.value = other.value;
    }

    /**
     * Performs increment.
     */
    public void increment(){
        this.value = this.value.increment();
    }

    /**
     * Performs decrement.
     */
    public void decrement(){
        this.value = this.value.decrement();
    }

    /**
     * Performs incrementBy.
     * @param other the other value
     */
    public void incrementBy(IntAccumulator other){
        this.value = this.value.plus(other.value);
    }

    /**
     * Performs incrementBy.
     * @param other the other value
     */
    public void incrementBy(Int other){
        this.value = this.value.plus(other);
    }
    
    /**
     * Performs times.
     * @param other the other value
     * @return the result of times
     */
    public IntAccumulator times(IntAccumulator other) {
        return new IntAccumulator(this.value.times(other.value));
    }

    /**
     * Performs get.
     * @return the result of get
     */
    public Int get() {
        return value;
    }

    /**
     * Performs compareTo.
     * @param o the o value
     * @return the result of compareTo
     */
    @Override
    /**
     * Performs compareTo.
     * @param o the o value
     * @return the result of compareTo
     */
    public int compareTo(Int o) {
       return this.value.compareTo(o);
    }

    /**
     * Performs sign.
     * @return the result of sign
     */
    @Override
    /**
     * Performs sign.
     * @return the result of sign
     */
    public int sign() {
        return value.sign();
    }
    
    /**
     * Checks whether is Zero.
     * @return the result of isZero
     */
    public boolean isZero () {
        return value.isZero();
    }
    
    /**
     * Returns to Big Integer.
     * @return the result of toBigInteger
     */
    public BigInteger toBigInteger() {
        return value.toBigInteger();
    }

    /**
     * Performs negate.
     * @return the result of negate
     */
    @Override
    /**
     * Performs negate.
     * @return the result of negate
     */
    public IntAccumulator negate() {
        return new IntAccumulator(value.negate());
    }

    /**
     * Performs incrementTimes.
     * @param other the other value
     */
    void incrementTimes(Int other) {
        this.value = this.value.times(other);
    }

    /**
     * Performs incrementTimes.
     * @param other the other value
     */
    void incrementTimes(IntAccumulator other) {
        this.value = this.value.times(other.value);
    }

    /**
     * Performs timesPlusTimes.
     * @param a the a value
     * @param b the b value
     * @param c the c value
     * @return the result of timesPlusTimes
     */
    IntAccumulator timesPlusTimes(IntAccumulator a, IntAccumulator b, IntAccumulator c) {
        // by usage a and b are not zero. this and c can be
         if (this.isZero()){
            if (c.isZero()){
                return this;
            } else {
                return b.times(c);
            }
         } else {
             if (c.isZero()){
                 return this.times(a);
             }
         }
         //this * a + b * c
         return new IntAccumulator(this.value.times(a.value).plus(b.value.times(c.value)));
    }

    /**
     * Performs timesPlusTimes.
     * @param a the a value
     * @param b the b value
     * @param c the c value
     */
    void timesPlusTimes(Int a, IntAccumulator b, Int c) {
        //this * a + b * c
        this.value = this.value.times(a).plus(b.value.times(c));
    }
}
