package org.midheaven.math;

import org.midheaven.lang.Ordered;
import org.midheaven.lang.Signed;

import java.math.BigInteger;

public final class IntAccumulator implements Ordered<Int>, Signed<IntAccumulator> {
    
    Int value;

    public IntAccumulator(){
        value = Int.ZERO;
    }

    public IntAccumulator(Int initialValue){
         value = initialValue;
    }

    public IntAccumulator(IntAccumulator other){
        this.value = other.value;
    }

    public void increment(){
        this.value = this.value.increment();
    }

    public void decrement(){
        this.value = this.value.decrement();
    }

    public void incrementBy(IntAccumulator other){
        this.value = this.value.plus(other.value);
    }

    public void incrementBy(Int other){
        this.value = this.value.plus(other);
    }
    
    public IntAccumulator times(IntAccumulator other) {
        return new IntAccumulator(this.value.times(other.value));
    }

    public Int get() {
        return value;
    }

    @Override
    public int compareTo(Int o) {
       return this.value.compareTo(o);
    }

    @Override
    public int sign() {
        return value.sign();
    }
    
    public boolean isZero () {
        return value.isZero();
    }
    
    public BigInteger toBigInteger() {
        return value.toBigInteger();
    }

    @Override
    public IntAccumulator negate() {
        return new IntAccumulator(value.negate());
    }

    void incrementTimes(Int other) {
        this.value = this.value.times(other);
    }

    void incrementTimes(IntAccumulator other) {
        this.value = this.value.times(other.value);
    }

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

    void timesPlusTimes(Int a, IntAccumulator b, Int c) {
        //this * a + b * c
        this.value = this.value.times(a).plus(b.value.times(c));
    }
}
