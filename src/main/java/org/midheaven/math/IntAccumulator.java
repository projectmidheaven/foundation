package org.midheaven.math;

import org.midheaven.lang.Ordered;
import org.midheaven.lang.Signed;

import java.math.BigInteger;

public final class IntAccumulator implements Ordered<Int>, Signed<IntAccumulator> {

    boolean primitive = true;
    long value;
    BigInteger bigValue;

    public IntAccumulator(){}

    public IntAccumulator(Int initialValue){
        if (initialValue instanceof BigInt){
            primitive = false;
            bigValue = initialValue.toBigInteger();
        } else {
            value = initialValue.toLong();
        }
    }

    public IntAccumulator(IntAccumulator other){
        this.value = other.value;
        this.primitive = other.primitive;
        this.bigValue = other.bigValue;
    }

    public void increment(){
        if (primitive){
            try {
                value = Math.addExact(value, 1);
            } catch (ArithmeticException e){
                primitive = false;
                bigValue = BigInteger.valueOf(value).add(BigInteger.ONE);
            }
        } else {
            bigValue = bigValue.add(BigInteger.ONE);
        }
    }

    public void decrement(){
        if (primitive){
            try {
                value = Math.subtractExact(value, 1);
            } catch (ArithmeticException e){
                primitive = false;
                bigValue = BigInteger.valueOf(value).subtract(BigInteger.ONE);
            }
        } else {
            bigValue = bigValue.subtract(BigInteger.ONE);
        }
    }

    public void incrementBy(IntAccumulator other){
        if (primitive && other.primitive){
           try {
                value = Math.addExact(value, other.value);
            } catch (ArithmeticException e){
                primitive = false;
                bigValue = BigInteger.valueOf(value).add(other.toBigInteger());
            }
        } else {
            bigValue = bigValue.add(other.toBigInteger());
        }
    }

    public void incrementBy(Int other){
        if (primitive){
            if (other instanceof Int32 || other instanceof Int64){
                try {
                    value = Math.addExact(value, other.toLong());
                } catch (ArithmeticException e){
                    primitive = false;
                    bigValue = BigInteger.valueOf(value).add(other.toBigInteger());
                }
            } else {
                primitive = false;
                bigValue = BigInteger.valueOf(value).add(other.toBigInteger());
            }
        } else {
            bigValue = bigValue.add(other.toBigInteger());
        }
    }

    public Int times(Int other) {
        if (primitive){
            if (other instanceof Int32 || other instanceof Int64){
                try {
                    return new Int64(Math.multiplyExact(value, other.toLong()));
                } catch (ArithmeticException e){
                    return this.get().times(other);
                }
            }
        }
        return this.get().times(other);
    }

    public IntAccumulator times(IntAccumulator other) {
        var c = new IntAccumulator();
        if (this.primitive && other.primitive){
            try {
                c.primitive = true;
                c.value = Math.addExact(value, other.value);
            } catch (ArithmeticException e){
                c.primitive = false;
                c.bigValue = BigInteger.valueOf(value).add(BigInteger.valueOf(other.value));
            }
        } else {
            c.primitive = false;
            c.bigValue = bigValue.add(BigInteger.valueOf(other.value));
        }
        return c;
    }

    public void decrementBy(Int other){
        if (primitive){
            if (other instanceof Int32 || other instanceof Int64){
                try {
                    value = Math.subtractExact(value, other.toLong());
                } catch (ArithmeticException e){
                    primitive = false;
                    bigValue = BigInteger.valueOf(value).subtract(other.toBigInteger());
                }
            } else {
                primitive = false;
                bigValue = BigInteger.valueOf(value).subtract(other.toBigInteger());
            }
        } else {
            bigValue = bigValue.subtract(other.toBigInteger());
        }
    }

    public Int get() {
        return primitive ? new Int64(value).reduce() : Int.of(bigValue);
    }

    @Override
    public int compareTo(Int o) {
        if (primitive && !(o instanceof BigInt)){
            return Long.compare(this.value, o.toLong());
        }
        return this.get().compareTo(o);
    }

    @Override
    public int sign() {
        return primitive ? Long.signum(value) : bigValue.signum();
    }

    public BigInteger toBigInteger() {
        return primitive ? BigInteger.valueOf(value) : bigValue;
    }

    @Override
    public IntAccumulator negate() {
        return new IntAccumulator(get().negate());
    }

    public void incrementTimes(Int other) {
        if (primitive){
            if (other instanceof Int32 || other instanceof Int64){
                try {
                    value = Math.multiplyExact(value, other.toLong());
                } catch (ArithmeticException e){
                    primitive = false;
                    bigValue = BigInteger.valueOf(value).multiply(other.toBigInteger());
                }
            } else {
                primitive = false;
                bigValue = BigInteger.valueOf(value).multiply(other.toBigInteger());
            }
        } else {
            bigValue = bigValue.multiply(other.toBigInteger());
        }
    }

    public void incrementTimes(IntAccumulator other) {
        if (primitive && other.primitive){
            try {
                value = Math.multiplyExact(value, other.value);
            } catch (ArithmeticException e){
                primitive = false;
                bigValue = BigInteger.valueOf(value).multiply(other.toBigInteger());
            }
        } else {
            bigValue = bigValue.multiply(other.toBigInteger());
        }
    }

    public IntAccumulator timesPlusTimes(IntAccumulator a, IntAccumulator b, IntAccumulator c) {
         //this * a + b * c
         var r = new IntAccumulator(this);

         if (r.primitive && a.primitive && b.primitive && c.primitive){
             try {
                 r.value = Math.addExact(Math.multiplyExact(r.value, a.value) , Math.multiplyExact(b.value, c.value));
             } catch (ArithmeticException e){
                 r.primitive = false;
                 r.bigValue = BigInteger.valueOf(r.value).multiply(a.toBigInteger()).add(b.toBigInteger().add(c.toBigInteger()));
             }
         } else {
             r.primitive = false;
             r.bigValue = r.toBigInteger().multiply(a.toBigInteger()).add(b.toBigInteger().add(c.toBigInteger()));
         }

         return r;
    }

    public void timesPlusTimes(Int a, IntAccumulator b, Int c) {
        //this * a + b * c
        if (primitive &&  b.primitive && !(a instanceof BigInt) && !(c instanceof BigInt)){
            try {
                this.value = Math.addExact(Math.multiplyExact(this.value, a.toLong()) , Math.multiplyExact(b.value, c.toLong()));
            } catch (ArithmeticException e){
                this.primitive = false;
                this.bigValue = BigInteger.valueOf(this.value).multiply(a.toBigInteger()).add(b.toBigInteger().add(c.toBigInteger()));
            }
        } else {
            this.primitive = false;
            this.bigValue = this.bigValue.multiply(a.toBigInteger()).add(b.toBigInteger().add(c.toBigInteger()));
        }
    }
}
