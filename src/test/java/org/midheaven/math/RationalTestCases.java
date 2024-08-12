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
    public void isParseable(){
        assertEquals(Rational.of(12345, 100) , Rational.parse(Rational.parse("123.45").toString()));
        assertEquals(Rational.ONE , Rational.parse("123 / 123"));
        assertEquals(Rational.ONE , Rational.parse(" 2 / 2 "));

        assertThrows(IllegalArgumentException.class, () ->  Rational.parse(" 1 / 1.5 "));
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

    @Test
    public void floorIsCorrect(){
        var a = Rational.of(4, 13);
        var b = Rational.of(21, 17);
        var c = Rational.of(4);
        var d = Rational.of(-4, 13);
        var f = Rational.of(-21, 17);

        assertEquals(Rational.ZERO, a.floor());
        assertEquals(Rational.ONE, b.floor());
        assertEquals(c, c.floor());
        assertEquals(Rational.from(Math.floor(d.toBigDecimal().doubleValue())), d.floor());
        assertEquals(Rational.from(Math.floor(f.toBigDecimal().doubleValue())), f.floor());
    }

    @Test
    public void ceilIsCorrect(){
        var a = Rational.of(4, 13);
        var b = Rational.of(21, 17);
        var c = Rational.of(4);
        var d = Rational.of(-4, 13);
        var f = Rational.of(-21, 17);

        assertEquals(Rational.ONE, a.ceil());
        assertEquals(Rational.of(2), b.ceil());
        assertEquals(c, c.ceil());
        assertEquals(Rational.ZERO, d.ceil());
        assertEquals(Rational.ONE.negate(), f.ceil());
    }
}
