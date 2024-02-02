package org.midheaven.math;

import org.junit.jupiter.api.Test;
import org.midheaven.collections.Enumerable;
import org.midheaven.collections.Sequence;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RationalTestCases {

    @Test
    public void hashCodesAreTheSame(){
        var a = Rational.of(32, 13);
        var b = Rational.of(BigInteger.valueOf(32), BigInteger.valueOf(13));

        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void sumIsCorrect(){
        var a = Rational.of(4, 13);
        var b = Rational.of(BigInteger.valueOf(5), BigInteger.valueOf(17));

        assertEquals(a, a.plus(Rational.ZERO));
        assertEquals(a, Rational.ZERO.plus(a));
        assertEquals(b, b.plus(Rational.ZERO));
        assertEquals(b, Rational.ZERO.plus(b));
        assertEquals(Rational.of(133, 221), a.plus(b));
    }

    @Test
    public void subtractionIsCorrect(){
        var a = Rational.of(4, 13);
        var b = Rational.of(BigInteger.valueOf(5), BigInteger.valueOf(17));

        assertEquals(a, a.minus(Rational.ZERO));
        assertEquals(a.negate(), Rational.ZERO.minus(a));
        assertEquals(b, b.minus(Rational.ZERO));
        assertEquals(b.negate(), Rational.ZERO.minus(b));
        assertEquals(Rational.of(3, 221), a.minus(b));
    }

    @Test
    public void multiplicationIsCorrect(){
        var a = Rational.of(4, 13);
        var b = Rational.of(BigInteger.valueOf(5), BigInteger.valueOf(17));

        assertEquals(Rational.ZERO, a.times(Rational.ZERO));
        assertEquals(Rational.ZERO, Rational.ZERO.times(a));
        assertEquals(Rational.ZERO, b.times(Rational.ZERO));
        assertEquals(Rational.ZERO, Rational.ZERO.times(b));
        assertEquals(Rational.of(20, 221), a.times(b));
    }

    @Test
    public void divisionIsCorrect(){
        var a = Rational.of(4, 13);
        var b = Rational.of(BigInteger.valueOf(5), BigInteger.valueOf(17));

        assertThrows(ArithmeticException.class, () -> a.over(Rational.ZERO));
        assertEquals(Rational.ZERO, Rational.ZERO.over(a));
        assertThrows(ArithmeticException.class, () -> b.over(Rational.ZERO));
        assertEquals(Rational.ZERO, Rational.ZERO.over(b));
        assertEquals(Rational.of(68, 65), a.over(b));
    }

    @Test
    public void enumerableSum(){
        var rationalEnumerable = Enumerable.iterate(Rational.ONE, it -> it.plus(1))
                .map(it -> it.invert())
                .limit(10)
                .with(Rational.arithmetic());

        assertEquals(Rational.of(7381,2520),  rationalEnumerable.sum());
        assertEquals(Rational.of(7381,25200),  rationalEnumerable.average());

    }

    @Test
    public void enumerableAllRationalSum(){
        var rationalSequence = Sequence.builder().of(1,2,3,4,5,6,7).map(Rational::of).with(Rational.arithmetic());

        var sum = rationalSequence.sum();

        var avg = rationalSequence.average();

        assertEquals(sum.over(7) , avg);

        assertEquals (sum, Sequence.builder().of(1,2,3,4,5,6,7).map(Rational::of).reduce(Rational.ZERO, Rational::plus));
        assertEquals (sum, Sequence.builder().of(1,2,3,4,5,6,7).map(Rational::of).reduce(Rational::plus).orElse(null));
    }
}
